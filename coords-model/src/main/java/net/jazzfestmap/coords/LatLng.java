package net.jazzfestmap.coords;

/**
 * Created by Сергей on 18.02.2017.
 */
public class LatLng {

    private Double latitude;
    private Double longitude;
    private CoordsStatus status;

    public LatLng(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static LatLng undetected() {
        LatLng latLng = new LatLng(0.0, 0.0);
        latLng.setStatus(CoordsStatus.UNDETECTED);
        return latLng;
    }

    public CoordsStatus getStatus() {
        return status;
    }

    public void setStatus(CoordsStatus status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
