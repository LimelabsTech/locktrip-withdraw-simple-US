package com.withdraw.simple.api;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class SimpleReservation extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b5060405160608061097483398101604090815281516020830151919092015160008054600160a060020a03338116600160a060020a031992831617909255600480549583169582169590951790945560018054938216938516939093179092556002805492909116919092161790556108e68061008e6000396000f3006080604052600436106100cf5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630bbfa1ba81146100d4578063715018a6146101005780638da5cb5b1461011757806392cd8306146101485780639b9b724914610160578063b34fe5a514610181578063b3feeac3146101ab578063b8ce65d3146101c0578063bc09d387146101e1578063bee9d69a146101f6578063ddc6e98214610211578063df708a3714610232578063f03af08314610247578063f2fde38b1461025f575b600080fd5b3480156100e057600080fd5b506100ec600435610280565b604080519115158252519081900360200190f35b34801561010c57600080fd5b506101156103cf565b005b34801561012357600080fd5b5061012c61043f565b60408051600160a060020a039092168252519081900360200190f35b34801561015457600080fd5b506100ec60043561044e565b34801561016c57600080fd5b50610115600160a060020a036004351661058f565b34801561018d57600080fd5b506101996004356105d9565b60408051918252519081900360200190f35b3480156101b757600080fd5b5061012c6105eb565b3480156101cc57600080fd5b50610115600160a060020a03600435166105fa565b3480156101ed57600080fd5b5061012c610644565b34801561020257600080fd5b506100ec600435602435610653565b34801561021d57600080fd5b50610115600160a060020a03600435166107a1565b34801561023e57600080fd5b5061012c6107eb565b34801561025357600080fd5b506101996004356107fa565b34801561026b57600080fd5b50610115600160a060020a0360043516610803565b600154600090819081903390600160a060020a038083169116146102a357600080fd5b6102ac426107fa565b9250600085116102bb57600080fd5b8483116102c757600080fd5b6000858152600360209081526040808320546004805460025484517fa9059cbb000000000000000000000000000000000000000000000000000000008152600160a060020a0391821693810193909352602483018490529351929750929092169363a9059cbb93604480850194919392918390030190829087803b15801561034e57600080fd5b505af1158015610362573d6000803e3d6000fd5b505050506040513d602081101561037857600080fd5b5051151561038257fe5b600254604080518481529051600160a060020a03909216917fef232fa02b2633bddc860db9d0d9b1c57fd83b506398b134edaebb6783d41e949181900360200190a2506001949350505050565b60005433600160a060020a039081169116146103ea57600080fd5b60008054604051600160a060020a03909116917ff8df31144d9c2f0f6b59d69b8b98abd5459d07f2742c4df920b25aae33c6482091a26000805473ffffffffffffffffffffffffffffffffffffffff19169055565b600054600160a060020a031681565b60015460009081903390600160a060020a0380831691161461046f57600080fd5b6000841161047c57600080fd5b42841061048857600080fd5b6000848152600360209081526040808320546004805460025484517fa9059cbb000000000000000000000000000000000000000000000000000000008152600160a060020a0391821693810193909352602483018490529351929750929092169363a9059cbb93604480850194919392918390030190829087803b15801561050f57600080fd5b505af1158015610523573d6000803e3d6000fd5b505050506040513d602081101561053957600080fd5b5051151561054357fe5b600254604080518481529051600160a060020a03909216917fef232fa02b2633bddc860db9d0d9b1c57fd83b506398b134edaebb6783d41e949181900360200190a25060019392505050565b60005433600160a060020a039081169116146105aa57600080fd5b6004805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b60036020526000908152604090205481565b600454600160a060020a031681565b60005433600160a060020a0390811691161461061557600080fd5b6002805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b600254600160a060020a031681565b60008080841161066257600080fd5b61066b426107fa565b905082811061067957600080fd5b600083815260036020526040902054610698908563ffffffff61082a16565b6000848152600360209081526040808320939093556004805484517f23b872dd000000000000000000000000000000000000000000000000000000008152600160a060020a03338116938201939093523083166024820152604481018a905294519116936323b872dd9360648083019493928390030190829087803b15801561072057600080fd5b505af1158015610734573d6000803e3d6000fd5b505050506040513d602081101561074a57600080fd5b5051151561075457fe5b60408051848152602081018690528151600160a060020a033316927f4f28e91d6ed1f91db2c9ffb49054b5b880ce891ce84e161b445d3bc18339abc1928290030190a25060019392505050565b60005433600160a060020a039081169116146107bc57600080fd5b6001805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b600154600160a060020a031681565b62015180900490565b60005433600160a060020a0390811691161461081e57600080fd5b6108278161083d565b50565b8181018281101561083757fe5b92915050565b600160a060020a038116151561085257600080fd5b60008054604051600160a060020a03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a36000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03929092169190911790555600a165627a7a7230582091dca23b4b490bd5a4f172c22b0bea1dc89ada7ab4f399707cb4cf9e45c24d710029";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<>();
    }

    protected SimpleReservation(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SimpleReservation(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<LogCreateReservationEventResponse> getLogCreateReservationEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("LogCreateReservation", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<LogCreateReservationEventResponse> responses = new ArrayList<LogCreateReservationEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogCreateReservationEventResponse typedResponse = new LogCreateReservationEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._customerAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._reservationCheckOutDate = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._reservationCostLOC = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogCreateReservationEventResponse> logCreateReservationEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("LogCreateReservation", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogCreateReservationEventResponse>() {
            @Override
            public LogCreateReservationEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                LogCreateReservationEventResponse typedResponse = new LogCreateReservationEventResponse();
                typedResponse.log = log;
                typedResponse._customerAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._reservationCheckOutDate = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._reservationCostLOC = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<LogWithdrawalEventResponse> getLogWithdrawalEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("LogWithdrawal", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<LogWithdrawalEventResponse> responses = new ArrayList<LogWithdrawalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogWithdrawalEventResponse typedResponse = new LogWithdrawalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.withdrawerAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.withdrawedAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogWithdrawalEventResponse> logWithdrawalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("LogWithdrawal", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogWithdrawalEventResponse>() {
            @Override
            public LogWithdrawalEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                LogWithdrawalEventResponse typedResponse = new LogWithdrawalEventResponse();
                typedResponse.log = log;
                typedResponse.withdrawerAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.withdrawedAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<OwnershipRenouncedEventResponse> getOwnershipRenouncedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("OwnershipRenounced", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<OwnershipRenouncedEventResponse> responses = new ArrayList<OwnershipRenouncedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipRenouncedEventResponse typedResponse = new OwnershipRenouncedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnershipRenouncedEventResponse> ownershipRenouncedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("OwnershipRenounced", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnershipRenouncedEventResponse>() {
            @Override
            public OwnershipRenouncedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                OwnershipRenouncedEventResponse typedResponse = new OwnershipRenouncedEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("OwnershipTransferred", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("OwnershipTransferred", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                "renounceOwnership", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> reservationsWithdrawMap(BigInteger param0) {
        final Function function = new Function("reservationsWithdrawMap", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> LOCTokenContract() {
        final Function function = new Function("LOCTokenContract", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> withdrawDestinationWalletAddress() {
        final Function function = new Function("withdrawDestinationWalletAddress", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> withdrawerAddress() {
        final Function function = new Function("withdrawerAddress", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String _newOwner) {
        final Function function = new Function(
                "transferOwnership", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<SimpleReservation> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _locTokenContractAddress, String _withdrawerAddress, String _withdrawDestinationWalletAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_locTokenContractAddress), 
                new org.web3j.abi.datatypes.Address(_withdrawerAddress), 
                new org.web3j.abi.datatypes.Address(_withdrawDestinationWalletAddress)));
        return deployRemoteCall(SimpleReservation.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<SimpleReservation> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _locTokenContractAddress, String _withdrawerAddress, String _withdrawDestinationWalletAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_locTokenContractAddress), 
                new org.web3j.abi.datatypes.Address(_withdrawerAddress), 
                new org.web3j.abi.datatypes.Address(_withdrawDestinationWalletAddress)));
        return deployRemoteCall(SimpleReservation.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public RemoteCall<TransactionReceipt> setLOCTokenContractAddress(String locTokenContractAddress) {
        final Function function = new Function(
                "setLOCTokenContractAddress", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(locTokenContractAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setWithdrawerAddress(String _withdrawerAddress) {
        final Function function = new Function(
                "setWithdrawerAddress", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_withdrawerAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setWithdrawDestinationWalletAddress(String _withdrawDestinationWalletAddress) {
        final Function function = new Function(
                "setWithdrawDestinationWalletAddress", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_withdrawDestinationWalletAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> formatTimestampToDaysOnly(BigInteger _dateToConvert) {
        final Function function = new Function("formatTimestampToDaysOnly", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_dateToConvert)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> createReservation(BigInteger _reservationCostLOC, BigInteger _reservationCheckOutDate) {
        final Function function = new Function(
                "createReservation", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_reservationCostLOC), 
                new org.web3j.abi.datatypes.generated.Uint256(_reservationCheckOutDate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdrawReservation(BigInteger _dateForWithdraw) {
        final Function function = new Function(
                "withdrawReservation", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_dateForWithdraw)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdrawReservationWithLargerTimestamp(BigInteger _dateForWithdraw) {
        final Function function = new Function(
                "withdrawReservationWithLargerTimestamp", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_dateForWithdraw)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static SimpleReservation load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleReservation(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static SimpleReservation load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleReservation(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class LogCreateReservationEventResponse {
        public Log log;

        public String _customerAddress;

        public BigInteger _reservationCheckOutDate;

        public BigInteger _reservationCostLOC;
    }

    public static class LogWithdrawalEventResponse {
        public Log log;

        public String withdrawerAddress;

        public BigInteger withdrawedAmount;
    }

    public static class OwnershipRenouncedEventResponse {
        public Log log;

        public String previousOwner;
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }
}
