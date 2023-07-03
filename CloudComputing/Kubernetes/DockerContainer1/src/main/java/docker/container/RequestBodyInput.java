package docker.container;

public class RequestBodyInput {
    
    private String fileName;
    private String productName;

    public RequestBodyInput() {
    }

    public RequestBodyInput(String fileName, String productName) {
        this.fileName = fileName;
        this.productName = productName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
