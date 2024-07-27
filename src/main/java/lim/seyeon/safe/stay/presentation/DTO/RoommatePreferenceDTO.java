package lim.seyeon.safe.stay.presentation.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.RoommatePreference.RoommatePreference;

public class RoommatePreferenceDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Integer bedtime;

    @NotNull
    private Integer noise;

    @NotNull
    private Integer cleanliness;

    @NotNull
    private Integer visitors;

    @NotNull
    private Integer smoking;

    @NotNull
    private Integer drinking;

    @NotNull
    private Integer pets;

    @NotNull
    private Integer interaction;

    @NotNull
    private Integer gender;

    @NotNull
    private Integer culture;

    @NotNull
    private Integer lang;

    public RoommatePreferenceDTO() {}

    public RoommatePreferenceDTO(Long userId, Integer bedtime, Integer noise, Integer cleanliness,
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

    public static RoommatePreference toEntity(RoommatePreferenceDTO roommatePreferenceDTO) {
        RoommatePreference roommatePreference = new RoommatePreference(
                roommatePreferenceDTO.getUserId(),
                roommatePreferenceDTO.getBedtime(),
                roommatePreferenceDTO.getNoise(),
                roommatePreferenceDTO.getCleanliness(),
                roommatePreferenceDTO.getVisitors(),
                roommatePreferenceDTO.getSmoking(),
                roommatePreferenceDTO.getDrinking(),
                roommatePreferenceDTO.getPets(),
                roommatePreferenceDTO.getInteraction(),
                roommatePreferenceDTO.getGender(),
                roommatePreferenceDTO.getCulture(),
                roommatePreferenceDTO.getLang()
        );
        return roommatePreference;
    }

    public static RoommatePreferenceDTO toDTO(RoommatePreference roommatePreference) {
        RoommatePreferenceDTO roommatePreferenceDTO = new RoommatePreferenceDTO(
                roommatePreference.getUserId(),
                roommatePreference.getBedtime(),
                roommatePreference.getNoise(),
                roommatePreference.getCleanliness(),
                roommatePreference.getVisitors(),
                roommatePreference.getSmoking(),
                roommatePreference.getDrinking(),
                roommatePreference.getPets(),
                roommatePreference.getInteraction(),
                roommatePreference.getGender(),
                roommatePreference.getCulture(),
                roommatePreference.getLang()
        );
        return roommatePreferenceDTO;
    }
}
