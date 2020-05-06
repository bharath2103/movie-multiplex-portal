package dtos;

import play.data.validation.Constraints;

public class CreateMultiplexDto {

    private Long id;

    @Constraints.Required(message = "Multiplex name not provided")
    private String multiplexName;

    @Constraints.Required(message = "Address name not provided")
    private String address;

    @Constraints.Required(message = "ScreenCount name not provided")
    private Integer numberOfScreens;

    public CreateMultiplexDto() {
    }

    public CreateMultiplexDto(Long id, String multiplexName, String address, Integer numberOfScreens) {
        this.id = id;
        this.multiplexName = multiplexName;
        this.address = address;
        this.numberOfScreens = numberOfScreens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMultiplexName() {
        return multiplexName;
    }

    public void setMultiplexName(String multiplexName) {
        this.multiplexName = multiplexName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNumberOfScreens() {
        return numberOfScreens;
    }

    public void setNumberOfScreens(Integer numberOfScreens) {
        this.numberOfScreens = numberOfScreens;
    }

    @Override
    public String toString() {
        return "createDto{" +
                "id=" + id +
                ", multiplexName='" + multiplexName + '\'' +
                ", address='" + address + '\'' +
                ", numberOfScreens=" + numberOfScreens +
                '}';
    }
}
