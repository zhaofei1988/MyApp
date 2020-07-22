package everlinkintl.com.myapp.datatemplate;

import java.util.List;

public class FirstTab {
    private int id;
    private int user_id;
    private int cargo_location_id;
    private String filepaths;
    private int status;
    private String address;
    private String geo;
    private String notes;
    private String created_at;
    private String updated_at;
    private CargoLocation cargo_location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCargo_location_id() {
        return cargo_location_id;
    }

    public void setCargo_location_id(int cargo_location_id) {
        this.cargo_location_id = cargo_location_id;
    }

    public String getFilepaths() {
        return filepaths;
    }

    public void setFilepaths(String filepaths) {
        this.filepaths = filepaths;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public CargoLocation getCargo_location() {
        return cargo_location;
    }

    public void setCargo_location(CargoLocation cargo_location) {
        this.cargo_location = cargo_location;
    }
}
