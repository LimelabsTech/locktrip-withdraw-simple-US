package com.withdraw.simple.api;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
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
public class SimpleReservationMultipleWithdrawers extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b5060405160408061090f83398101604052805160209091015160008054600160a060020a03338116600160a060020a03199283161790925560038054929094169116179091556001556108a7806100686000396000f3006080604052600436106100ae5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416635068265081146100b3578063715018a61461011c5780638da5cb5b146101335780639b9b724914610164578063a1b416f914610185578063b3feeac3146101c5578063de2edd6a146101da578063e8d3d17b14610204578063edb87b361461021c578063f2fde38b14610243578063fe2b392414610264575b600080fd5b3480156100bf57600080fd5b5060408051602060048035808201358381028086018501909652808552610108953695939460249493850192918291850190849080828437509497506102b99650505050505050565b604080519115158252519081900360200190f35b34801561012857600080fd5b50610131610321565b005b34801561013f57600080fd5b50610148610391565b60408051600160a060020a039092168252519081900360200190f35b34801561017057600080fd5b50610131600160a060020a03600435166103a0565b34801561019157600080fd5b5061019d6004356103ea565b60408051600160a060020a039094168452602084019290925282820152519081900360600190f35b3480156101d157600080fd5b50610148610416565b3480156101e657600080fd5b50610108600435602435604435600160a060020a0360643516610425565b34801561021057600080fd5b506101316004356105c5565b34801561022857600080fd5b506102316105e5565b60408051918252519081900360200190f35b34801561024f57600080fd5b50610131600160a060020a03600435166105eb565b34801561027057600080fd5b5060408051602060048035808201358381028086018501909652808552610108953695939460249493850192918291850190849080828437509497506106129650505050505050565b600080826001548151111515156102cf57600080fd5b83516000106102dd57600080fd5b600091505b83518210156103175761030b84838151811015156102fc57fe5b9060200190602002015161079e565b506001909101906102e2565b5060019392505050565b60005433600160a060020a0390811691161461033c57600080fd5b60008054604051600160a060020a03909116917ff8df31144d9c2f0f6b59d69b8b98abd5459d07f2742c4df920b25aae33c6482091a26000805473ffffffffffffffffffffffffffffffffffffffff19169055565b600054600160a060020a031681565b60005433600160a060020a039081169116146103bb57600080fd5b6003805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b6002602081905260009182526040909120805460018201549190920154600160a060020a039092169183565b600354600160a060020a031681565b6000848152600260205260408120548590600160a060020a03161561044957600080fd5b6000851161045657600080fd5b42841161046257600080fd5b60408051606081018252600160a060020a038581168252602080830189815283850189815260008c815260028085528782209651875473ffffffffffffffffffffffffffffffffffffffff1916908716178755925160018701559051949091019390935560035484517f23b872dd00000000000000000000000000000000000000000000000000000000815233841660048201523084166024820152604481018b905294519216936323b872dd93606480830194928390030190829087803b15801561052d57600080fd5b505af1158015610541573d6000803e3d6000fd5b505050506040513d602081101561055757600080fd5b5051151561056157fe5b82600160a060020a031633600160a060020a031687600019167fbfdcb244c63c7f97edf0d080fd642c9768de7e994163221f7db7574d1aaf50048789604051808381526020018281526020019250505060405180910390a450600195945050505050565b60005433600160a060020a039081169116146105e057600080fd5b600155565b60015481565b60005433600160a060020a0390811691161461060657600080fd5b61060f816107eb565b50565b60008060008060008560015481511115151561062d57600080fd5b865160001061063b57600080fd5b600093505b86518410156106af57868481518110151561065757fe5b6020908102909101810151600081815260029092526040909120909350915061067f8361079e565b50600182015461069690869063ffffffff61086816565b9450600082600101819055508380600101945050610640565b600354604080517fa9059cbb000000000000000000000000000000000000000000000000000000008152600160a060020a033381166004830152602482018990529151919092169163a9059cbb9160448083019260209291908290030181600087803b15801561071e57600080fd5b505af1158015610732573d6000803e3d6000fd5b505050506040513d602081101561074857600080fd5b5051151561075257fe5b604080518681529051600160a060020a033316917fef232fa02b2633bddc860db9d0d9b1c57fd83b506398b134edaebb6783d41e94919081900360200190a25060019695505050505050565b60008181526002602052604081205433600160a060020a039081169116146107c557600080fd5b6000828152600260208190526040909120015442116107e357600080fd5b506001919050565b600160a060020a038116151561080057600080fd5b60008054604051600160a060020a03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a36000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b8181018281101561087557fe5b929150505600a165627a7a72305820e2cd66c6b18286c30f8c58fd1a6afdbd73a7a08b2babd96427ed52760d2a48b20029";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<>();
    }

    protected SimpleReservationMultipleWithdrawers(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SimpleReservationMultipleWithdrawers(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<LogCreateReservationEventResponse> getLogCreateReservationEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("LogCreateReservation", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<LogCreateReservationEventResponse> responses = new ArrayList<LogCreateReservationEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogCreateReservationEventResponse typedResponse = new LogCreateReservationEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._reservationId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._customerAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._recipientAddress = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse._reservationEndDate = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._reservationCostLOC = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogCreateReservationEventResponse> logCreateReservationEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("LogCreateReservation", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogCreateReservationEventResponse>() {
            @Override
            public LogCreateReservationEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                LogCreateReservationEventResponse typedResponse = new LogCreateReservationEventResponse();
                typedResponse.log = log;
                typedResponse._reservationId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._customerAddress = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._recipientAddress = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse._reservationEndDate = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
            typedResponse._withdrawerAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._withdrawedAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse._withdrawerAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._withdrawedAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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

    public RemoteCall<Tuple3<String, BigInteger, BigInteger>> reservations(byte[] param0) {
        final Function function = new Function("reservations", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple3<String, BigInteger, BigInteger>>(
                new Callable<Tuple3<String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<String> LOCTokenContract() {
        final Function function = new Function("LOCTokenContract", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> maxAllowedWithdrawCyclesCount() {
        final Function function = new Function("maxAllowedWithdrawCyclesCount", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String _newOwner) {
        final Function function = new Function(
                "transferOwnership", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<SimpleReservationMultipleWithdrawers> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _locTokenContractAddress, BigInteger _maxAllowedCyclesCount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_locTokenContractAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_maxAllowedCyclesCount)));
        return deployRemoteCall(SimpleReservationMultipleWithdrawers.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<SimpleReservationMultipleWithdrawers> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _locTokenContractAddress, BigInteger _maxAllowedCyclesCount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_locTokenContractAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_maxAllowedCyclesCount)));
        return deployRemoteCall(SimpleReservationMultipleWithdrawers.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public RemoteCall<TransactionReceipt> setLOCTokenContractAddress(String _locTokenContractAddress) {
        final Function function = new Function(
                "setLOCTokenContractAddress", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_locTokenContractAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setmaxAllowedWithdrawCyclesCount(BigInteger _maxAllowedWithdrawCyclesCount) {
        final Function function = new Function(
                "setmaxAllowedWithdrawCyclesCount", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_maxAllowedWithdrawCyclesCount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> validateWithdraw(List<byte[]> _reservationIds) {
        final Function function = new Function("validateWithdraw", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.Utils.typeMap(_reservationIds, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> createReservation(byte[] _reservationId, BigInteger _reservationCostLOC, BigInteger _startDateForWithdrawal, String _recipientAddress) {
        final Function function = new Function(
                "createReservation", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_reservationId), 
                new org.web3j.abi.datatypes.generated.Uint256(_reservationCostLOC), 
                new org.web3j.abi.datatypes.generated.Uint256(_startDateForWithdrawal), 
                new org.web3j.abi.datatypes.Address(_recipientAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdraw(List<byte[]> _reservationIds) {
        final Function function = new Function(
                "withdraw", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.Utils.typeMap(_reservationIds, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static SimpleReservationMultipleWithdrawers load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleReservationMultipleWithdrawers(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static SimpleReservationMultipleWithdrawers load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleReservationMultipleWithdrawers(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class LogCreateReservationEventResponse {
        public Log log;

        public byte[] _reservationId;

        public String _customerAddress;

        public String _recipientAddress;

        public BigInteger _reservationEndDate;

        public BigInteger _reservationCostLOC;
    }

    public static class LogWithdrawalEventResponse {
        public Log log;

        public String _withdrawerAddress;

        public BigInteger _withdrawedAmount;
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
