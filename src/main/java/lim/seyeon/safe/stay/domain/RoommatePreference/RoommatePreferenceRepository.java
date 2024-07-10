package lim.seyeon.safe.stay.domain.RoommatePreference;

import lim.seyeon.safe.stay.domain.House.House;

import java.util.List;

public interface RoommatePreferenceRepository {
    RoommatePreference add(RoommatePreference roommatePreference);
    RoommatePreference findRoommatePreferenceByUserId(Long user_id);
    List<RoommatePreference> findAll();
    RoommatePreference update(RoommatePreference roommatePreference);
    void delete(Long user_id);
}
