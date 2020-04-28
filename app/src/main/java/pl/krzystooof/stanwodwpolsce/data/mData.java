package pl.krzystooof.stanwodwpolsce.data;

import com.google.gson.annotations.SerializedName;

public class mData {

    @SerializedName("id_stacji")
    String stationId;

    @SerializedName ("stacja")
    String stationName;

    @SerializedName ("rzeka")
    String riverName;

    @SerializedName ("województwo")
    String voivodeshipName;

    @SerializedName ("stan_wody")
    String waterAmount;

    @SerializedName ("stan_wody_data_pomiaru")
    String waterAmountDate;

    @SerializedName ("temperatura_wody")
    String waterTemperature;

    @SerializedName ("temperatura_wody_data_pomiaru")
    String waterTemperatureDate;

    @SerializedName ("zjawisko_lodowe")
    String icingAmount;

    @SerializedName ("zjawisko_lodowe_data_pomiaru")
    String icingAmountDate;

    @SerializedName ("zjawisko_zarastania")
    String overgrowAmount;

    @SerializedName ("zjawisko_zarastania_data_pomiaru")
    String overgrowAmountDate;

    public mData(String stationId, String stationName, String riverName, String voivodeshipName, String waterAmount, String waterAmountDate, String waterTemperature, String waterTemperatureDate, String icingAmount, String icingAmountDate, String overgrowAmount, String overgrowAmountDate) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.riverName = riverName;
        this.voivodeshipName = voivodeshipName;
        this.waterAmount = waterAmount;
        this.waterAmountDate = waterAmountDate;
        this.waterTemperature = waterTemperature;
        this.waterTemperatureDate = waterTemperatureDate;
        this.icingAmount = icingAmount;
        this.icingAmountDate = icingAmountDate;
        this.overgrowAmount = overgrowAmount;
        this.overgrowAmountDate = overgrowAmountDate;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getRiverName() {
        return riverName;
    }

    public void setRiverName(String riverName) {
        this.riverName = riverName;
    }

    public String getVoivodeshipName() {
        return voivodeshipName;
    }

    public void setVoivodeshipName(String voivodeshipName) {
        this.voivodeshipName = voivodeshipName;
    }

    public String getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(String waterAmount) {
        this.waterAmount = waterAmount;
    }

    public String getWaterAmountDate() {
        return waterAmountDate;
    }

    public void setWaterAmountDate(String waterAmountDate) {
        this.waterAmountDate = waterAmountDate;
    }

    public String getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(String waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public String getWaterTemperatureDate() {
        return waterTemperatureDate;
    }

    public void setWaterTemperatureDate(String waterTemperatureDate) {
        this.waterTemperatureDate = waterTemperatureDate;
    }

    public String getIcingAmount() {
        return icingAmount;
    }

    public void setIcingAmount(String icingAmount) {
        this.icingAmount = icingAmount;
    }

    public String getIcingAmountDate() {
        return icingAmountDate;
    }

    public void setIcingAmountDate(String icingAmountDate) {
        this.icingAmountDate = icingAmountDate;
    }

    public String getOvergrowAmount() {
        return overgrowAmount;
    }

    public void setOvergrowAmount(String overgrowAmount) {
        this.overgrowAmount = overgrowAmount;
    }

    public String getOvergrowAmountDate() {
        return overgrowAmountDate;
    }

    public void setOvergrowAmountDate(String overgrowAmountDate) {
        this.overgrowAmountDate = overgrowAmountDate;
    }
}
