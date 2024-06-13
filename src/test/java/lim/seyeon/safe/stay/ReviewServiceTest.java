package lim.seyeon.safe.stay;

import lim.seyeon.safe.stay.application.HouseService;
import lim.seyeon.safe.stay.application.ReviewService;
import lim.seyeon.safe.stay.application.UserServiceImpl;
import lim.seyeon.safe.stay.domain.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.ReviewRepository;
import lim.seyeon.safe.stay.presentation.HouseDTO;
import lim.seyeon.safe.stay.presentation.ReviewDTO;
import lim.seyeon.safe.stay.presentation.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 추가 후 조회 테스트")
    void addAndFindReviewByIdIntegration() {
        UserDTO userDTO = new UserDTO( "username", "password");
        HouseDTO houseDTO = new HouseDTO( "houseName", "address", "city", "state", "zipCode", 100.0, "description");
        UserDTO savedUserDTO = userService.save(userDTO);
        HouseDTO savedHouseDTO = houseService.add(houseDTO);

        ReviewDTO reviewDTO = new ReviewDTO(savedUserDTO.getId(), savedHouseDTO.getId(), 5, "title", "content", LocalDateTime.now());

        ReviewDTO savedReviewDTO = reviewService.add(reviewDTO);
        ReviewDTO foundReviewDTO = reviewService.findReviewById(savedReviewDTO.getId());

        assertNotNull(savedReviewDTO);
        assertNotNull(foundReviewDTO);
        assertEquals(savedReviewDTO.getId(), foundReviewDTO.getId());
        assertEquals(savedReviewDTO.getUserid(), foundReviewDTO.getUserid());
        assertEquals(savedReviewDTO.getHouseid(), foundReviewDTO.getHouseid());
        assertEquals(savedReviewDTO.getStars(), foundReviewDTO.getStars());
        assertEquals(savedReviewDTO.getTitle(), foundReviewDTO.getTitle());
        assertEquals(savedReviewDTO.getContent(), foundReviewDTO.getContent());
        Duration duration = Duration.between(savedReviewDTO.getCreatedat(), foundReviewDTO.getCreatedat());
        assertTrue(Math.abs(duration.getSeconds()) <= 1, "createdAt timestamps differ by more than 1 second");
    }

    @Test
    @DisplayName("모든 리뷰 조회 테스트")
    void findAllReviewsIntegration() {
        UserDTO userDTO = new UserDTO( "username", "password");
        HouseDTO houseDTO = new HouseDTO("houseName", "address", "city", "state", "zipCode", 100.0, "description");
        UserDTO savedUserDTO = userService.save(userDTO);
        HouseDTO savedHouseDTO = houseService.add(houseDTO);

        ReviewDTO reviewDTO1 = new ReviewDTO(savedUserDTO.getId(), savedHouseDTO.getId(), 5, "title1", "content1", LocalDateTime.now());
        ReviewDTO reviewDTO2 = new ReviewDTO(savedUserDTO.getId(), savedHouseDTO.getId(), 4, "title2", "content2", LocalDateTime.now().minusDays(1));

        reviewService.add(reviewDTO1);
        reviewService.add(reviewDTO2);

        List<ReviewDTO> reviewDTOs = reviewService.findAll();

        assertNotNull(reviewDTOs);
        assertEquals(2, reviewDTOs.size());
    }

    @Test
    @DisplayName("유저 ID로 리뷰 조회 테스트")
    void findReviewsByUserIdIntegration() {
        UserDTO userDTO = new UserDTO("username", "password");
        HouseDTO houseDTO = new HouseDTO( "houseName", "address", "city", "state", "zipCode", 100.0, "description");
        UserDTO savedUserDTO = userService.save(userDTO);
        HouseDTO savedHouseDTO = houseService.add(houseDTO);

        ReviewDTO reviewDTO1 = new ReviewDTO(savedUserDTO.getId(), savedHouseDTO.getId(), 5, "title1", "content1", LocalDateTime.now());
        ReviewDTO reviewDTO2 = new ReviewDTO(savedUserDTO.getId(), savedHouseDTO.getId(), 4, "title2", "content2", LocalDateTime.now().minusDays(1));

        reviewService.add(reviewDTO1);
        reviewService.add(reviewDTO2);

        List<ReviewDTO> reviewDTOs = reviewService.findReviewsByUserId(savedUserDTO.getId());

        assertNotNull(reviewDTOs);
        assertEquals(2, reviewDTOs.size());
    }

    @Test
    @DisplayName("집 ID로 리뷰 조회 테스트")
    void findReviewsByHouseIdIntegration() {
        UserDTO userDTO = new UserDTO( "username", "password");
        HouseDTO houseDTO = new HouseDTO( "houseName", "address", "city", "state", "zipCode", 100.0, "description");
        UserDTO savedUserDTO = userService.save(userDTO);
        HouseDTO savedHouseDTO = houseService.add(houseDTO);

        ReviewDTO reviewDTO1 = new ReviewDTO(savedUserDTO.getId(), savedHouseDTO.getId(), 5, "title1", "content1", LocalDateTime.now());
        ReviewDTO reviewDTO2 = new ReviewDTO(savedUserDTO.getId(), savedHouseDTO.getId(), 4, "title2", "content2", LocalDateTime.now().minusDays(1));

        reviewService.add(reviewDTO1);
        reviewService.add(reviewDTO2);

        List<ReviewDTO> reviewDTOs = reviewService.findReviewsByHouseId(savedHouseDTO.getId());

        assertNotNull(reviewDTOs);
        assertEquals(2, reviewDTOs.size());
    }

    @Test
    @DisplayName("리뷰 업데이트 테스트")
    void updateReviewIntegration() {
        UserDTO userDTO = new UserDTO( "username", "password");
        HouseDTO houseDTO = new HouseDTO( "houseName", "address", "city", "state", "zipCode", 100.0, "description");
        UserDTO savedUserDTO = userService.save(userDTO);
        HouseDTO savedHouseDTO = houseService.add(houseDTO);

        ReviewDTO reviewDTO = new ReviewDTO(savedUserDTO.getId(), savedHouseDTO.getId(), 5, "title", "content", LocalDateTime.now());

        ReviewDTO savedReviewDTO = reviewService.add(reviewDTO);
        ReviewDTO updatedReviewDTO = new ReviewDTO(savedReviewDTO.getId(), savedReviewDTO.getUserid(), savedReviewDTO.getHouseid(), 4, "updated title", "updated content", LocalDateTime.now());

        ReviewDTO resultReviewDTO = reviewService.update(updatedReviewDTO);

        assertNotNull(resultReviewDTO);
        assertEquals(savedReviewDTO.getId(), resultReviewDTO.getId());
        assertEquals("updated title", resultReviewDTO.getTitle());
        assertEquals("updated content", resultReviewDTO.getContent());
        assertEquals(4, resultReviewDTO.getStars());
    }

    @Test
    @DisplayName("리뷰 삭제 테스트")
    void deleteReviewIntegration() {
        UserDTO userDTO = new UserDTO( "username", "password");
        HouseDTO houseDTO = new HouseDTO( "houseName", "address", "city", "state", "zipCode", 100.0, "description");
        UserDTO savedUserDTO = userService.save(userDTO);
        HouseDTO savedHouseDTO = houseService.add(houseDTO);

        ReviewDTO reviewDTO = new ReviewDTO(savedUserDTO.getId(), savedHouseDTO.getId(), 5, "title", "content", LocalDateTime.now());
        ReviewDTO savedReviewDTO = reviewService.add(reviewDTO);

        reviewService.delete(savedReviewDTO.getId());

        assertThrows(EntityNotFoundException.class, () -> reviewService.findReviewById(savedReviewDTO.getId()));
    }
}
