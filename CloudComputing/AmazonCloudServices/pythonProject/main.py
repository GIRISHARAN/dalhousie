# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

from concurrent import futures

import grpc
import computeandstorage_pb2
import computeandstorage_pb2_grpc
import s3_operations


def print_hi(name):
    print(f'Hi, {name}')


class EC2Operations(computeandstorage_pb2_grpc.EC2OperationsServicer):

    def StoreData(self, request, context):
        return computeandstorage_pb2.StoreReply(s3uri=s3_operations.storeDataToObject(request.data))

    def AppendData(self, request, context):
        s3_operations.storeDataToObject(request.data)
        return computeandstorage_pb2.AppendReply()

    def DeleteFile(self, request, context):
        s3_operations.deleteObject()
        return computeandstorage_pb2.DeleteReply()


def serve():
    port = "50051"
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    computeandstorage_pb2_grpc.add_EC2OperationsServicer_to_server(EC2Operations(), server)
    server.add_insecure_port("[::]:" + port)
    server.start()
    print("Server started, listening on " + port)
    server.wait_for_termination()


if __name__ == '__main__':
    print_hi('PyCharm')
    serve()
