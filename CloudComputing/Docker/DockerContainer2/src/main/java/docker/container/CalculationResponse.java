package docker.container;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CalculationResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String file;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer sum;

    // Getters and setters

    public CalculationResponse(String file, String error) {
        this.file = file;
        this.error = error;
    }

    public CalculationResponse(String file, Integer sum) {
        this.file = file;
        this.sum = sum;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}