public class Customer extends User {

    private String licenseNo;
    
    public Customer(String name, String contact, String email, String password, String licenseNo) {
        super(name, contact, email, password);
        this.licenseNo = licenseNo;
    }

    // Getters and Setters
    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

}


