package docker.container;

public class InputData {

    private String file;
    private String product;
    private String data;
    
    public InputData() {
    }
    
    
    public InputData(String file, String product, String inputFileData) {
        this.file = file;
        this.product = product;
        this.data = inputFileData;
    }


    public String getFile() {
        return file;
    }
    public void setFile(String file) {
        this.file = file;
    }
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public String getData() {
        return data;
    }
    public void setData(String inputFileData) {
        this.data = inputFileData;
    }    
}