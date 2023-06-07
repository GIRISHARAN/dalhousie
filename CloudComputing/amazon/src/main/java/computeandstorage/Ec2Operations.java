package computeandstorage;

import org.lognet.springboot.grpc.GRpcService;

@GRpcService()
public class Ec2Operations extends computeandstorage.EC2OperationsGrpc.EC2OperationsImplBase {
    //Store Data Method
    public void storeData(computeandstorage.Computeandstorage.StoreRequest request,
                          io.grpc.stub.StreamObserver<computeandstorage.Computeandstorage.StoreReply> responseObserver) {
        System.out.println(request.getData());
        computeandstorage.Computeandstorage.StoreReply storeReply = computeandstorage.Computeandstorage.StoreReply.newBuilder().setS3Uri(S3Operations.insertDataToS3Object(request.getData())).build();
        responseObserver.onNext(storeReply);
        responseObserver.onCompleted();
    }

    // Appened Data
    public void appendData(computeandstorage.Computeandstorage.AppendRequest request,
                           io.grpc.stub.StreamObserver<computeandstorage.Computeandstorage.AppendReply> responseObserver) {
        System.out.println(request.getData());
        S3Operations.insertDataToS3Object(request.getData());
        computeandstorage.Computeandstorage.AppendReply appendReply = computeandstorage.Computeandstorage.AppendReply.newBuilder().build();
        responseObserver.onNext(appendReply);
        responseObserver.onCompleted();
    }

    // Delete Data
    public void deleteFile(computeandstorage.Computeandstorage.DeleteRequest request,
                           io.grpc.stub.StreamObserver<computeandstorage.Computeandstorage.DeleteReply> responseObserver) {
        System.out.println(request.getS3Uri());
        S3Operations.deleteObject();
        computeandstorage.Computeandstorage.DeleteReply deleteReply = computeandstorage.Computeandstorage.DeleteReply.newBuilder().build();
        responseObserver.onNext(deleteReply);
        responseObserver.onCompleted();
    }
}
