package com.withdraw.simple.api;

import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

public class SimpleWithdraw {

	// The same configuration as the rest-api, except this should be the withdrawer
	// user
	static String jsonString = "{\"version\":3,\"id\":\"d44f162d-1f91-4ade-9d5f-414661295df0\",\"address\":\"b63df2068d209f8ff3925c4c9dbbabfd31301825\",\"Crypto\":{\"ciphertext\":\"2d40317fc74b4ea71930a0a6681507addc103e127e65a663cf731537ef79726d\",\"cipherparams\":{\"iv\":\"f90df4df3a67d85e7e34f84a7c0b15fd\"},\"cipher\":\"aes-128-ctr\",\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"salt\":\"473b462c67127e8260fea0ff7fe716d17b6792278160937a29d8875294d0f92a\",\"n\":8192,\"r\":8,\"p\":1},\"mac\":\"b94eada113e11314895cb559bd79e72c0dc27eb15ed4ebb666ffb80977859f13\"}}";
	static String password = "123456789";
	// This is the address of thе contract when deployed
	static String SimpleReservationSingleWithdrawerContractAddress = "0x504fb529fe3c899d609404f4ba0a6875e169a26a";
	static String SimpleReservationCustomWithdrawerContractAddress = "0xac146Feb6c7812d2c6BF448e1AdF45cEC3C19163";
	static String LocTokenContractAddress = "0xa8ef317aaafea769e4b6df9f43d070ccaaa31dfb";
	// This should be changed for different environments, can be used with a config
	// file. This is the same as the ress-api
	static Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io/Up5uvBHSCSqtOmnlhL87"));
	// static Web3j web3 = Web3j.build(new HttpService());

	// Method for creating the creadentials for interacting with the contract
	public static Credentials createCredentials() throws IOException, CipherException {

		File tempWalletFile = File.createTempFile("temp-wallet-file", ".tmp");
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempWalletFile));
		writer.write(jsonString);
		writer.close();
		tempWalletFile.deleteOnExit();

		Credentials credentials = WalletUtils.loadCredentials(password, tempWalletFile);
		return credentials;
	}
	
	public static TransactionReceipt createSimpleReservation(int reservationCostWei,int timestampInSeconds) throws IOException, CipherException, SmartContractException {
		
		
		//Creating reseravation gas limit can be set to 85000. This is the limit used in the frontend.
		SimpleReservationSingleWithdrawer simpleReservationContractInstance = SimpleReservationSingleWithdrawer.load(
				SimpleReservationSingleWithdrawerContractAddress, web3, createCredentials(),
				ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
		
		
		//Gas limi for approve can be set to 60000. This is the limit used in the frontend.
		MintableToken locTokenContractInstance = MintableToken.load(
				LocTokenContractAddress, web3, createCredentials(),
				ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
		
		
		int daysInSeconds = 86400;
		long formattedTimestamp = timestampInSeconds / daysInSeconds;
		BigInteger timestampConverted = BigInteger.valueOf(formattedTimestamp);
		BigInteger reservationCostConverted = BigInteger.valueOf(reservationCostWei);
		
		TransactionReceipt approve = null;
		BigInteger allowance = null;
		
		try {
			allowance = locTokenContractInstance.allowance("0xb63df2068d209f8ff3925c4c9dbbabfd31301825", SimpleReservationSingleWithdrawerContractAddress).send();
			System.out.println(allowance);
		} catch (Exception e) {
			throw new SmartContractException("Allowance");
		}
		
		
		try {
			approve = locTokenContractInstance.approve(SimpleReservationSingleWithdrawerContractAddress, reservationCostConverted).send();
			
		} catch (Exception e) {
			throw new SmartContractException("Approve failed");
		}
		
		TransactionReceipt createReservation = null;
		
		try {
			createReservation = simpleReservationContractInstance.createReservation(reservationCostConverted, timestampConverted).send();
			
		} catch (Exception e) {
			throw new SmartContractException("Creating reservataion failed");
		}
		
		return createReservation;
		
	}

	// This is called by the owner of the contract to set the withdrawer user
	// All of this functions can accept parameter for the address, not to be
	// hardcoded in the transaction
	public static TransactionReceipt setWithdrawer() throws IOException, CipherException, SmartContractException {
		SimpleReservationSingleWithdrawer simpleReservationContractInstance = SimpleReservationSingleWithdrawer.load(
				SimpleReservationSingleWithdrawerContractAddress, web3, createCredentials(),
				ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
		TransactionReceipt setWithdrawer = null;
		try {
			setWithdrawer = simpleReservationContractInstance
					.setWithdrawerAddress("0xb63df2068d209f8ff3925c4c9dbbabfd31301825").send();
		} catch (Exception e) {
			throw new SmartContractException("Setting the withdrawer address failed");
		}
		return setWithdrawer;
	}

	// This is called by the owner to set the withdrawer destination address (to
	// whom the money should be withdrawn)
	public static TransactionReceipt setWithdrawerDestinationAddress()
			throws IOException, CipherException, SmartContractException {
		SimpleReservationSingleWithdrawer simpleReservationContractInstance = SimpleReservationSingleWithdrawer.load(
				SimpleReservationSingleWithdrawerContractAddress, web3, createCredentials(),
				ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
		TransactionReceipt setWithdrawerDestinationAddress = null;
		try {
			setWithdrawerDestinationAddress = simpleReservationContractInstance
					.setWithdrawDestinationWalletAddress("0xb63df2068d209f8ff3925c4c9dbbabfd31301825").send();
		} catch (Exception e) {
			throw new SmartContractException("Setting the withdrawer destination address failed");
		}
		return setWithdrawerDestinationAddress;
	}

	// Getting the withdrawer address
	public static String getWithdrawerAddress() throws SmartContractException, IOException, CipherException {
		SimpleReservationSingleWithdrawer simpleReservationContractInstance = SimpleReservationSingleWithdrawer.load(
				SimpleReservationSingleWithdrawerContractAddress, web3, createCredentials(),
				ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

		String withdrawerAddress = null;

		try {
			withdrawerAddress = simpleReservationContractInstance.withdrawerAddress().send();
		} catch (Exception e) {
			throw new SmartContractException("Getting the withdrawer address failed");
		}
		System.out.println(withdrawerAddress);
		return withdrawerAddress;
	}

	// Getting the withdraw destination wallet address
	public static String getWithdrawerDestinationAddress() throws SmartContractException, IOException, CipherException {
		SimpleReservationSingleWithdrawer simpleReservationContractInstance = SimpleReservationSingleWithdrawer.load(
				SimpleReservationSingleWithdrawerContractAddress, web3, createCredentials(),
				ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

		String withdrawerDestinationAddress = null;

		try {
			withdrawerDestinationAddress = simpleReservationContractInstance.withdrawDestinationWalletAddress().send();
		} catch (Exception e) {
			throw new SmartContractException("Getting the withdrawer destination address failed");
		}
		System.out.println(withdrawerDestinationAddress);
		return withdrawerDestinationAddress;
	}

	// This will try to withdraw the reservations for the given timestamp (the
	// timestamp should be in seconds)
	public static TransactionReceipt withdrawReservationsForGivenTimestamp(int timestampInSeconds)
			throws Exception {
		SimpleReservationSingleWithdrawer simpleReservationContractInstance = SimpleReservationSingleWithdrawer.load(
				SimpleReservationSingleWithdrawerContractAddress, web3, createCredentials(),
				ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
		
		

		int daysInSeconds = 86400;
		long formattedTimestamp = timestampInSeconds / daysInSeconds;
		System.out.println(formattedTimestamp);
		BigInteger timestampConverted = BigInteger.valueOf(formattedTimestamp);
		System.out.println(timestampConverted);
		
		
		 BigInteger isValidTimeStamp = simpleReservationContractInstance.reservationsWithdrawMap(timestampConverted).send();
		 if(isValidTimeStamp.intValue() == 0) {
			 throw new SmartContractException("No reservations to withdraw for this date");
		 }
	
		 
		TransactionReceipt withdrawResult;
		try {
			withdrawResult = simpleReservationContractInstance.withdrawReservation(timestampConverted).send();
		} catch (Exception e) {
			throw new SmartContractException("Withdraw failed");
		}
		System.out.println(withdrawResult);
		return withdrawResult;
	}

	
	//Withdraw funds from contract with custom withdrawer, again we will withdraw only reservations that the recipient address is equal to ours
	public static TransactionReceipt withdrawFromCustomWithdrawerConctract(String[] reservationIds) throws IOException, CipherException, SmartContractException {
		SimpleReservationMultipleWithdrawers reservationCustomWithdrawerContractInstance = SimpleReservationMultipleWithdrawers.load(SimpleReservationCustomWithdrawerContractAddress, web3, createCredentials(), ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
			
		TransactionReceipt withdrawResult;
		ArrayList<byte[]> reservationIdsArray = new ArrayList<byte[]>();
		
		for (int i = 0; i < reservationIds.length; i++) {
			Bytes32 reservationIdBytes = stringToBytes32(reservationIds[i]);
			byte[] reservationIdByte = reservationIdBytes.getValue();
			reservationIdsArray.add(reservationIdByte);
		}
		
		try {
			withdrawResult = reservationCustomWithdrawerContractInstance.withdraw(reservationIdsArray).send();
			
		} catch (Exception e) {
			throw new SmartContractException("Withdraw failed");
		}
		System.out.println(withdrawResult);
		return withdrawResult;
	}
	
	public static Bytes32 stringToBytes32(String string) {
        byte[] byteValue = string.getBytes();
        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
        return new Bytes32(byteValueLen32);
    }

	public static void main(String[] args) throws Exception {
//		setWithdrawer();
//		setWithdrawerDestinationAddress();
//		 withdrawReservationsForGivenTimestamp(1534168800);
//		getWithdrawerAddress();
//		getWithdrawerDestinationAddress();
//		withdrawFromCustomWithdrawerConctract(reservationIds);
		createSimpleReservation(10, 1536523200);
	}
}
