package lim.seyeon.safe.stay.domain.RoommatePreference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roommate_preferences")
public class RoommatePreference {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "bedtime", nullable = false)
    private Integer bedtime;

    @Column(name = "noise", nullable = false)
    private Integer noise;

    @Column(name = "cleanliness", nullable = false)
    private Integer cleanliness;

    @Column(name = "visitors", nullable = false)
    private Integer visitors;

    @Column(name = "smoking", nullable = false)
    private Integer smoking;

    @Column(name = "drinking", nullable = false)
    private Integer drinking;

    @Column(name = "pets", nullable = false)
    private Integer pets;

    @Column(name = "interaction", nullable = false)
    private Integer interaction;

    @Column(name = "gender", nullable = false)
    private Integer gender;

    @Column(name = "culture", nullable = false)
    private Integer culture;

    @Column(name = "lang", nullable = false)
    private Integer lang;

    public RoommatePreference() {}

    public RoommatePreference(Long userId, Integer bedtime, Integer noise, Integer cleanliness,
                              Integer visitors, Integer smoking, Integer drinking, Integer pets,
                              Integer interaction, Integer gender, Integer culture, Integer lang) {
        this.userId = userId;
        this.bedtime = bedtime;
        this.noise = noise;
        this.cleanliness = cleanliness;
        this.visitors = visitors;
        this.smoking = smoking;
        this.drinking = drinking;
        this.pets = pets;
        this.interaction = interaction;
        this.gender = gender;
        this.culture = culture;
        this.lang = lang;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getBedtime() {
        return bedtime;
    }

    public Integer getNoise() {
        return noise;
    }

    public Integer getCleanliness() {
        return cleanliness;
    }

    public Integer getVisitors() {
        return visitors;
    }

    public Integer getSmoking() {
        return smoking;
    }

    public Integer getDrinking() {
        return drinking;
    }

    public Integer getPets() {
        return pets;
    }

    public Integer getInteraction() {
        return interaction;
    }

    public Integer getGender() {
        return gender;
    }

    public Integer getCulture() {
        return culture;
    }

    public Integer getLang() {
        return lang;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBedtime(Integer bedtime) {
        this.bedtime = bedtime;
    }

    public void setNoise(Integer noise) {
        this.noise = noise;
    }

    public void setCleanliness(Integer cleanliness) {
        this.cleanliness = cleanliness;
    }

    public void setVisitors(Integer visitors) {
        this.visitors = visitors;
    }

    public void setSmoking(Integer smoking) {
        this.smoking = smoking;
    }

    public void setDrinking(Integer drinking) {
        this.drinking = drinking;
    }

    public void setPets(Integer pets) {
        this.pets = pets;
    }

    public void setInteraction(Integer interaction) {
        this.interaction = interaction;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setCulture(Integer culture) {
        this.culture = culture;
    }

    public void setLang(Integer lang) {
        this.lang = lang;
    }
}

