package com.withdraw.simple.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;


public class SimpleWithdraw {

	// The same configuration as the rest-api, except this should be the withdrawer
	// user
	static String jsonString = "{\"version\":3,\"id\":\"d44f162d-1f91-4ade-9d5f-414661295df0\",\"address\":\"b63df2068d209f8ff3925c4c9dbbabfd31301825\",\"Crypto\":{\"ciphertext\":\"2d40317fc74b4ea71930a0a6681507addc103e127e65a663cf731537ef79726d\",\"cipherparams\":{\"iv\":\"f90df4df3a67d85e7e34f84a7c0b15fd\"},\"cipher\":\"aes-128-ctr\",\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"salt\":\"473b462c67127e8260fea0ff7fe716d17b6792278160937a29d8875294d0f92a\",\"n\":8192,\"r\":8,\"p\":1},\"mac\":\"b94eada113e11314895cb559bd79e72c0dc27eb15ed4ebb666ffb80977859f13\"}}";
	static String password = "123456789";
	// This is the address of thе contract when deployed
	static String SimpleReservationContractAddress = "0x030b1852cc0749f6e044479d55fe446d731f1386";
	// This should be changed for different environments, can be used with a config
	// file. This is the same as the ress-api
	static Web3j web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/Up5uvBHSCSqtOmnlhL87"));
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

	// This is called by the owner of the contract to set the withdrawer user
	// All of this functions can accept parameter for the address, not to be
	// hardcoded in the transaction
	public static TransactionReceipt setWithdrawer() throws IOException, CipherException, SmartContractException {
		SimpleReservation simpleReservationContractInstance = SimpleReservation.load(SimpleReservationContractAddress,
				web3, createCredentials(), ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
		TransactionReceipt setWithdrawer = null;
		try {
			setWithdrawer = simpleReservationContractInstance
					.setWithdrawerAddress("0x6524083C3A4B06CAc3Bb2D13c7C2BC3aeB50C680").send();
		} catch (Exception e) {
			throw new SmartContractException("Setting the withdrawer address failed");
		}
		return setWithdrawer;
	}

	// This is called by the owner to set the withdrawer destination address (to
	// whom the money should be withdrawn)
	public static TransactionReceipt setWithdrawerDestinationAddress()
			throws IOException, CipherException, SmartContractException {
		SimpleReservation simpleReservationContractInstance = SimpleReservation.load(SimpleReservationContractAddress,
				web3, createCredentials(), ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
		TransactionReceipt setWithdrawerDestinationAddress = null;
		try {
			setWithdrawerDestinationAddress = simpleReservationContractInstance
					.setWithdrawDestinationWalletAddress("0x6524083C3A4B06CAc3Bb2D13c7C2BC3aeB50C680").send();
		} catch (Exception e) {
			throw new SmartContractException("Setting the withdrawer destination address failed");
		}
		return setWithdrawerDestinationAddress;
	}

	// Getting the withdrawer address
	public static String getWithdrawerAddress() throws SmartContractException, IOException, CipherException {
		SimpleReservation simpleReservationContractInstance = SimpleReservation.load(SimpleReservationContractAddress,
				web3, createCredentials(), ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

		String withdrawerAddress = null;

		try {
			withdrawerAddress = simpleReservationContractInstance.withdrawerAddress().send();
		} catch (Exception e) {
			throw new SmartContractException("Getting the withdrawer address failed");
		}
		return withdrawerAddress;
	}

	// Getting the withdraw destination wallet address
	public static String getWithdrawerDestinationAddress() throws SmartContractException, IOException, CipherException {
		SimpleReservation simpleReservationContractInstance = SimpleReservation.load(SimpleReservationContractAddress,
				web3, createCredentials(), ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

		String withdrawerDestinationAddress = null;

		try {
			withdrawerDestinationAddress = simpleReservationContractInstance.withdrawDestinationWalletAddress().send();
		} catch (Exception e) {
			throw new SmartContractException("Getting the withdrawer destination address failed");
		}
		return withdrawerDestinationAddress;
	}

	// This will try to withdraw the reservations for the given timestamp (the
	// timestamp should be in seconds)
	public static TransactionReceipt withdrawReservationsForGivenTimestamp(int timestampInSeconds)
			throws IOException, CipherException, SmartContractException {
		SimpleReservation simpleReservationContractInstance = SimpleReservation.load(SimpleReservationContractAddress,
				web3, createCredentials(), ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

		int daysInSeconds = 86400;
		long formattedTimestamp = timestampInSeconds / daysInSeconds;
		System.out.println(formattedTimestamp);
		BigInteger timestampConverted = BigInteger.valueOf(formattedTimestamp);
		System.out.println(timestampConverted);

		TransactionReceipt withdrawResult;
		try {
			withdrawResult = simpleReservationContractInstance.withdrawReservation(timestampConverted).send();
		} catch (Exception e) {
			throw new SmartContractException("Withdraw failed");
		}
		System.out.println(withdrawResult);
		return withdrawResult;
	}

	public static void main(String[] args) throws Exception {
		setWithdrawer();
		setWithdrawerDestinationAddress();
		withdrawReservationsForGivenTimestamp(1530021600);
		getWithdrawerAddress();
		getWithdrawerDestinationAddress();
	}
}
