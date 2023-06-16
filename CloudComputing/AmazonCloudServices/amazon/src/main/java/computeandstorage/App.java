package computeandstorage;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Ec2Operations ec2Operations = new Ec2Operations();
        int port = 50051;
        Server server = ServerBuilder.forPort(port)
                .addService(ec2Operations)
                .build();

        server.start();
        System.out.println("Hello World!");
        server.awaitTermination();
    }
}