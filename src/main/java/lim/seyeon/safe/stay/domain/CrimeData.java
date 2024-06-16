package lim.seyeon.safe.stay.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "crime_data")
public class CrimeData {

    @Id
    private Integer dr_no;
    private String date_rptd;
    private String date_occ;
    private Integer time_occ;
    private Integer area;
    private String area_name;
    private Integer rpt_dist_no;
    private Integer part_12;
    private Integer crm_cd;
    private String crm_cd_desc;
    private String mocodes;
    private Integer vict_age;
    private String vict_sex;
    private String vict_descent;
    private Integer premis_cd;
    private String premis_desc;
    private String weapon_used;
    private String weapon_desc;
    private String status;
    private String status_desc;
    private Integer crm_cd_1;
    private Integer crm_cd_2;
    private Integer crm_cd_3;
    private Integer crm_cd_4;
    private String location;
    private String cross_street;
    private String lat;
    private String lon;

    public CrimeData() {}

    public CrimeData(Integer dr_no, String date_rptd, String date_occ, Integer time_occ, Integer area, String area_name, Integer rpt_dist_no, Integer part_12, Integer crm_cd, String crm_cd_desc, String mocodes, Integer vict_age, String vict_sex, String vict_descent, Integer premis_cd, String premis_desc, String weapon_used, String weapon_desc, String status, String status_desc, Integer crm_cd_1, Integer crm_cd_2, Integer crm_cd_3, Integer crm_cd_4, String location, String cross_street, String lat, String lon) {
        this.dr_no = dr_no;
        this.date_rptd = date_rptd;
        this.date_occ = date_occ;
        this.time_occ = time_occ;
        this.area = area;
        this.area_name = area_name;
        this.rpt_dist_no = rpt_dist_no;
        this.part_12 = part_12;
        this.crm_cd = crm_cd;
        this.crm_cd_desc = crm_cd_desc;
        this.mocodes = mocodes;
        this.vict_age = vict_age;
        this.vict_sex = vict_sex;
        this.vict_descent = vict_descent;
        this.premis_cd = premis_cd;
        this.premis_desc = premis_desc;
        this.weapon_used = weapon_used;
        this.weapon_desc = weapon_desc;
        this.status = status;
        this.status_desc = status_desc;
        this.crm_cd_1 = crm_cd_1;
        this.crm_cd_2 = crm_cd_2;
        this.crm_cd_3 = crm_cd_3;
        this.crm_cd_4 = crm_cd_4;
        this.location = location;
        this.cross_street = cross_street;
        this.lat = lat;
        this.lon = lon;
    }

    public Integer getDr_no() {
        return dr_no;
    }

    public String getDate_rptd() {
        return date_rptd;
    }

    public String getDate_occ() {
        return date_occ;
    }

    public Integer getTime_occ() {
        return time_occ;
    }

    public Integer getArea() {
        return area;
    }

    public String getArea_name() {
        return area_name;
    }

    public Integer getRpt_dist_no() {
        return rpt_dist_no;
    }

    public Integer getPart_12() {
        return part_12;
    }

    public Integer getCrm_cd() {
        return crm_cd;
    }

    public String getCrm_cd_desc() {
        return crm_cd_desc;
    }

    public String getMocodes() {
        return mocodes;
    }

    public Integer getVict_age() {
        return vict_age;
    }

    public String getVict_sex() {
        return vict_sex;
    }

    public String getVict_descent() {
        return vict_descent;
    }

    public Integer getPremis_cd() {
        return premis_cd;
    }

    public String getPremis_desc() {
        return premis_desc;
    }

    public String getWeapon_used() {
        return weapon_used;
    }

    public String getWeapon_desc() {
        return weapon_desc;
    }

    public String getStatus() {
        return status;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public Integer getCrm_cd_1() {
        return crm_cd_1;
    }

    public Integer getCrm_cd_2() {
        return crm_cd_2;
    }

    public Integer getCrm_cd_3() {
        return crm_cd_3;
    }

    public Integer getCrm_cd_4() {
        return crm_cd_4;
    }

    public String getLocation() {
        return location;
    }

    public String getCross_street() {
        return cross_street;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public void setDr_no(Integer dr_no) {
        this.dr_no = dr_no;
    }

    public void setDate_rptd(String date_rptd) {
        this.date_rptd = date_rptd;
    }

    public void setDate_occ(String date_occ) {
        this.date_occ = date_occ;
    }

    public void setTime_occ(Integer time_occ) {
        this.time_occ = time_occ;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public void setRpt_dist_no(Integer rpt_dist_no) {
        this.rpt_dist_no = rpt_dist_no;
    }

    public void setPart_12(Integer part_12) {
        this.part_12 = part_12;
    }

    public void setCrm_cd(Integer crm_cd) {
        this.crm_cd = crm_cd;
    }

    public void setCrm_cd_desc(String crm_cd_desc) {
        this.crm_cd_desc = crm_cd_desc;
    }

    public void setMocodes(String mocodes) {
        this.mocodes = mocodes;
    }

    public void setVict_age(Integer vict_age) {
        this.vict_age = vict_age;
    }

    public void setVict_sex(String vict_sex) {
        this.vict_sex = vict_sex;
    }

    public void setVict_descent(String vict_descent) {
        this.vict_descent = vict_descent;
    }

    public void setPremis_cd(Integer premis_cd) {
        this.premis_cd = premis_cd;
    }

    public void setPremis_desc(String premis_desc) {
        this.premis_desc = premis_desc;
    }

    public void setWeapon_used(String weapon_used) {
        this.weapon_used = weapon_used;
    }

    public void setWeapon_desc(String weapon_desc) {
        this.weapon_desc = weapon_desc;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public void setCrm_cd_1(Integer crm_cd_1) {
        this.crm_cd_1 = crm_cd_1;
    }

    public void setCrm_cd_2(Integer crm_cd_2) {
        this.crm_cd_2 = crm_cd_2;
    }

    public void setCrm_cd_3(Integer crm_cd_3) {
        this.crm_cd_3 = crm_cd_3;
    }

    public void setCrm_cd_4(Integer crm_cd_4) {
        this.crm_cd_4 = crm_cd_4;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCross_street(String cross_street) {
        this.cross_street = cross_street;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }


}