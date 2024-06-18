package lim.seyeon.safe.stay;

import lim.seyeon.safe.stay.application.HouseService;
import lim.seyeon.safe.stay.application.ReviewService;
import lim.seyeon.safe.stay.application.UserServiceImpl;
import lim.seyeon.safe.stay.application.ValidationService;
import lim.seyeon.safe.stay.domain.*;
import lim.seyeon.safe.stay.presentation.HouseDTO;
import lim.seyeon.safe.stay.presentation.ReviewDTO;
import lim.seyeon.safe.stay.presentation.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceUnitTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ValidationService validationService;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private HouseService houseService;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    @DisplayName("리뷰 추가 후 조회 테스트")
    void addAndFindReviewById() {
        UserDTO userDTO = new UserDTO(1L, "username", "password");
        HouseDTO houseDTO = new HouseDTO(1L, "name1", "address1", "city1", "state1", "zipcode1", 100.0, "description1");
        ReviewDTO reviewDTO = new ReviewDTO(userDTO.getId(), houseDTO.getId(), 5, "title", "content", LocalDateTime.now());
        Review review = ReviewDTO.toEntity(reviewDTO, UserDTO.toEntity(userDTO), HouseDTO.toEntity(houseDTO));
        review.setId(1L);

        when(userService.findUserById(1L)).thenReturn(userDTO);
        when(houseService.findHouseById(1L)).thenReturn(houseDTO);
        when(reviewRepository.add(any(Review.class))).thenReturn(review);
        when(reviewRepository.findReviewById(1L)).thenReturn(review);

        ReviewDTO savedReviewDTO = reviewService.add(reviewDTO);
        ReviewDTO foundReviewDTO = reviewService.findReviewById(1L);

        assertNotNull(savedReviewDTO);
        assertNotNull(foundReviewDTO);
        assertEquals(savedReviewDTO.getId(), foundReviewDTO.getId());
        assertEquals(savedReviewDTO.getUserid(), foundReviewDTO.getUserid());
        assertEquals(savedReviewDTO.getHouseid(), foundReviewDTO.getHouseid());
        assertEquals(savedReviewDTO.getStars(), foundReviewDTO.getStars());
        assertEquals(savedReviewDTO.getTitle(), foundReviewDTO.getTitle());
        assertEquals(savedReviewDTO.getContent(), foundReviewDTO.getContent());
        assertEquals(savedReviewDTO.getCreatedat().truncatedTo(ChronoUnit.SECONDS), foundReviewDTO.getCreatedat().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    @DisplayName("존재하지 않는 리뷰 조회 테스트")
    void findNonExistingReviewById() {
        when(reviewRepository.findReviewById(1L)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> reviewService.findReviewById(1L));
    }

    @Test
    @DisplayName("모든 리뷰 조회 테스트")
    void findAllReviews() {
        User user1 = new User(1L, "username1", "password1");
        House house1 = new House(1L, "name1", "address1", "city1", "state1", "zipcode1", 100.0, "description1");

        User user2 = new User(2L, "username2", "password2");
        House house2 = new House(2L, "name2", "address2", "city2", "state2", "zipcode2", 200.0, "description2");

        ReviewDTO review1DTO = new ReviewDTO(user1.getId(), house1.getId(), 5, "title1", "content1", LocalDateTime.now());
        review1DTO.setId(1L);
        Review review1 = ReviewDTO.toEntity(review1DTO, user1, house1);
        ReviewDTO review2DTO = new ReviewDTO(user2.getId(), house2.getId(), 4, "title2", "content2", LocalDateTime.now());
        review2DTO.setId(2L);
        Review review2 = ReviewDTO.toEntity(review2DTO, user2, house2);

        when(reviewRepository.findAll()).thenReturn(Arrays.asList(review1, review2));

        List<ReviewDTO> reviewDTOs = reviewService.findAll();

        assertNotNull(reviewDTOs);
        assertEquals(2, reviewDTOs.size());
        assertEquals(1L, reviewDTOs.get(0).getUserid());
        assertEquals(2L, reviewDTOs.get(1).getUserid());
    }

    @Test
    @DisplayName("유저 ID로 리뷰 조회 테스트")
    void findReviewsByUserId() {
        UserDTO userDTO = new UserDTO(1L, "username", "password");
        HouseDTO houseDTO = new HouseDTO(1L, "name1", "address1", "city1", "state1", "zipcode1", 100.0, "description1");
        ReviewDTO reviewDTO = new ReviewDTO(userDTO.getId(), houseDTO.getId(), 5, "title", "content", LocalDateTime.now());
        Review review = ReviewDTO.toEntity(reviewDTO, UserDTO.toEntity(userDTO), HouseDTO.toEntity(houseDTO));
        review.setId(1L);

        when(reviewRepository.findReviewsByUserId(1L)).thenReturn(Arrays.asList(review));

        List<ReviewDTO> reviewDTOs = reviewService.findReviewsByUserId(1L);

        assertNotNull(reviewDTOs);
        assertEquals(1, reviewDTOs.size());
        assertEquals(1L, reviewDTOs.get(0).getUserid());
    }

    @Test
    @DisplayName("집 ID로 리뷰 조회 테스트")
    void findReviewsByHouseId() {
        UserDTO userDTO = new UserDTO(1L, "username", "password");
        HouseDTO houseDTO = new HouseDTO(1L, "name1", "address1", "city1", "state1", "zipcode1", 100.0, "description1");
        ReviewDTO reviewDTO = new ReviewDTO(userDTO.getId(), houseDTO.getId(), 5, "title", "content", LocalDateTime.now());
        Review review = ReviewDTO.toEntity(reviewDTO, UserDTO.toEntity(userDTO), HouseDTO.toEntity(houseDTO));
        review.setId(1L);

        when(reviewRepository.findReviewsByHouseId(1L)).thenReturn(Arrays.asList(review));

        List<ReviewDTO> reviewDTOs = reviewService.findReviewsByHouseId(1L);

        assertNotNull(reviewDTOs);
        assertEquals(1, reviewDTOs.size());
        assertEquals(1L, reviewDTOs.get(0).getHouseid());
    }

    @Test
    @DisplayName("리뷰 업데이트 테스트")
    void updateReview() {
        UserDTO userDTO = new UserDTO(1L, "username", "password");
        HouseDTO houseDTO = new HouseDTO(1L, "name1", "address1", "city1", "state1", "zipcode1", 100.0, "description1");
        ReviewDTO reviewDTO = new ReviewDTO(userDTO.getId(), houseDTO.getId(), 5, "updatedTitle", "updatedContent", LocalDateTime.now());
        Review review = ReviewDTO.toEntity(reviewDTO, UserDTO.toEntity(userDTO), HouseDTO.toEntity(houseDTO));
        review.setId(1L);

        when(userService.findUserById(1L)).thenReturn(userDTO);
        when(houseService.findHouseById(1L)).thenReturn(houseDTO);
        when(reviewRepository.update(any(Review.class))).thenReturn(review);

        ReviewDTO updatedReviewDTO = reviewService.update(reviewDTO);

        assertNotNull(updatedReviewDTO);
        assertEquals("updatedTitle", updatedReviewDTO.getTitle());
        assertEquals("updatedContent", updatedReviewDTO.getContent());
    }

    @Test
    @DisplayName("Throws EntityNotFoundException when a review with non-existent user id is queried")
    void updateReviewWithNonExistingUser() {
        ReviewDTO reviewDTO = new ReviewDTO(1L, 1L, 1L, 5, "title", "content", LocalDateTime.now());

        when(userService.findUserById(1L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> reviewService.update(reviewDTO));
    }

    @Test
    @DisplayName("Throws EntityNotFoundException when a review with non-existent house id is queried")
    void updateReviewWithNonExistingHouse() {
        UserDTO userDTO = new UserDTO(1L, "username", "password");
        ReviewDTO reviewDTO = new ReviewDTO(1L, 1L, 1L, 5, "title", "content", LocalDateTime.now());

        when(userService.findUserById(1L)).thenReturn(userDTO);
        when(houseService.findHouseById(1L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> reviewService.update(reviewDTO));
    }

    @Test
    @DisplayName("Review should be deleted successfully")
    void deleteReview() {
        doNothing().when(reviewRepository).delete(1L);

        reviewService.delete(1L);

        verify(reviewRepository, times(1)).delete(1L);
    }

}
