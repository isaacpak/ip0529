public class Tool extends ToolType {
    private final String toolCode;
    private final String brand;

    Tool(String toolCode, String toolType, String brand, double dailyCharge, boolean weekdayCharge,
            boolean weekendCharge, boolean holidayCharge) {
        super(toolType, dailyCharge, weekdayCharge, weekendCharge, holidayCharge);
        this.toolCode = toolCode;
        this.brand = brand;
    }

    public String getToolCode() {
        return toolCode;
    }

    public String getBrand() {
        return brand;
    }
    // Omitting Setters
}
