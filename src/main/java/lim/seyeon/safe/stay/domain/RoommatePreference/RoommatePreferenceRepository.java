package lim.seyeon.safe.stay.domain.RoommatePreference;

import lim.seyeon.safe.stay.presentation.Filter.RoommateFilter;

import java.util.List;

public interface RoommatePreferenceRepository {
    RoommatePreference add(RoommatePreference roommatePreference);
    RoommatePreference findRoommatePreferenceByUserId(Long user_id);
    List<RoommatePreference> findAll();
    RoommatePreference update(RoommatePreference roommatePreference);
    void delete(Long user_id);
    boolean exists(Long user_id);
    List<RoommatePreference> findRoommates(RoommateFilter filter, Long currentUserId);
    Integer getMatchRate(Long user_id1, Long user_id2);
    Integer addMatchRate(Long user_id1, Long user_id2);
    Integer getQuestionId(String questionKey);
    String getAnswerText(String questionKey, int answerNum);
}
