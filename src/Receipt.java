public class Receipt {
    
    private Rental rental;
    private Car car;
    private String manufactureYear;
    private String createdTime;

    public Receipt(Rental rental, Car car, String manufactureYear, String createdTime) {
        this.rental = rental;
        this.car = car;
        this.manufactureYear = manufactureYear;
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("\t\t\t\t+------------------------------------------------------------------------------------------+\n");
        sb.append("\t\t\t\t|                                     ****************                                     |\n");
        sb.append("\t\t\t\t|                                     *    RECEIPT   *                                     |\n");
        sb.append("\t\t\t\t|                                     ****************                                     |\n");
        sb.append("\t\t\t\t+------------------------------------------------------------------------------------------+\n");
        sb.append("\t\t\t\t|    Tel   : 03-2128016                                                                    |\n");
        sb.append("\t\t\t\t|    Email : ez4rsdn@gmail.com                                                             |\n");
        sb.append(String.format("\t\t\t\t|    Receipt created on : %-64s |\n", createdTime));
        sb.append("\t\t\t\t+------------------------------------------------------------------------------------------+\n");
        sb.append("\t\t\t\t|    Start Date           |    End Date             |    Renting Days                      |\n");
        sb.append("\t\t\t\t+-------------------------+-------------------------+--------------------------------------+\n");
        sb.append(String.format("\t\t\t\t|    %-20s |    %-20s |    %-33d |\n",
                rental.getStartDate(), rental.getEndDate(), rental.getRentalDays()));
        sb.append("\t\t\t\t+------------------------------------------------------------------------------------------+\n");
        sb.append("\t\t\t\t|    Vehicle Description                   |      Car Type         |         Price         |\n");
        sb.append("\t\t\t\t+------------------------------------------------------------------------------------------+\n");
        sb.append(String.format("\t\t\t\t|    %-10s %-26s |    %-18s |    $%-17.2f |\n",
                car.getBrand(), car.getModel(), car.getType(), car.getPricePerDay()));
        sb.append(String.format("\t\t\t\t|    Plate Number        : %-63s |\n", rental.getPlateNumber()));
        sb.append(String.format("\t\t\t\t|    Manufacture Year    : %-63s |\n", manufactureYear));
        sb.append(String.format("\t\t\t\t|    Price (per day)     : $%-62.2f |\n", car.getPricePerDay()));
        sb.append("\t\t\t\t+------------------------------------------------------------------------------------------+\n");
        sb.append(String.format("\t\t\t\t|    TOTAL RENTAL COST  : $%-63.2f |\n", rental.getRentalCost()));
        sb.append("\t\t\t\t+------------------------------------------------------------------------------------------+\n");

        return sb.toString();
    }
}
