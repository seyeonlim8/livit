package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.domain.House.House;
import lim.seyeon.safe.stay.domain.Neighborhood.Neighborhood;
import lim.seyeon.safe.stay.domain.Review.Review;
import lim.seyeon.safe.stay.domain.Review.ReviewRepository;
import lim.seyeon.safe.stay.domain.User.User;
import lim.seyeon.safe.stay.presentation.DTO.HouseDTO;
import lim.seyeon.safe.stay.presentation.DTO.NeighborhoodDTO;
import lim.seyeon.safe.stay.presentation.DTO.ReviewDTO;
import lim.seyeon.safe.stay.presentation.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final NeighborhoodService neighborhoodService;
    private ReviewRepository reviewRepository;
    private ValidationService validationService;
    private UserServiceImpl userService;
    private HouseService houseService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ValidationService validationService
    , UserServiceImpl userService, HouseService houseService, NeighborhoodService neighborhoodService) {
        this.reviewRepository = reviewRepository;
        this.validationService = validationService;
        this.userService = userService;
        this.houseService = houseService;
        this.neighborhoodService = neighborhoodService;
    }

    public ReviewDTO add(ReviewDTO reviewDTO) {
        UserDTO userDTO = userService.findUserById(reviewDTO.getUserid());
        if (userDTO == null) {
            throw new EntityNotFoundException("User with ID " + reviewDTO.getUserid() + " not found");
        }
        User user = UserDTO.toEntity(userDTO);

        HouseDTO houseDTO = houseService.findHouseById(reviewDTO.getHouseid());
        if (houseDTO == null) {
            throw new EntityNotFoundException("House with ID " + reviewDTO.getHouseid() + " not found");
        }

        NeighborhoodDTO neighborhoodDTO = neighborhoodService.findNeighborhoodByName(houseDTO.getNeighborhood());
        if(neighborhoodDTO == null) {
            throw new EntityNotFoundException("Neighborhood with name " + houseDTO.getNeighborhood() + " not found");
        }
        Neighborhood neighborhood = NeighborhoodDTO.toEntity(neighborhoodDTO);
        House house = HouseDTO.toEntity(houseDTO, neighborhood);

        Review review = ReviewDTO.toEntity(reviewDTO, user, house);
        validationService.checkValid(review);

        Review savedReview = reviewRepository.add(review);
        ReviewDTO savedReviewDTO = ReviewDTO.toDTO(savedReview);
        return savedReviewDTO;
    }

    public ReviewDTO findReviewById(Long id) {
        Review review = reviewRepository.findReviewById(id);
        if (review == null) {
            throw new EntityNotFoundException("Review with ID " + id + " not found");
        }
        ReviewDTO reviewDTO = ReviewDTO.toDTO(review);
        return reviewDTO;
    }

    public List<ReviewDTO> findAll() {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDTO> reviewDTOS = reviews.stream()
                .map(review -> ReviewDTO.toDTO(review))
                .toList();
        return reviewDTOS;
    }

    public List<ReviewDTO> findReviewsByUserId(Long userid) {
        List<Review> reviews = reviewRepository.findReviewsByUserId(userid);
        if (reviews == null) {
            throw new EntityNotFoundException("Review with User ID " + userid + " not found");
        }
        List<ReviewDTO> reviewDTOS = reviews.stream()
                .map(review -> ReviewDTO.toDTO(review))
                .toList();
        return reviewDTOS;
    }

    public List<ReviewDTO> findReviewsByHouseId(Long houseid) {
        List<Review> reviews = reviewRepository.findReviewsByHouseId(houseid);
        if (reviews == null) {
            throw new EntityNotFoundException("Review with House ID " + houseid + " not found");
        }
        List<ReviewDTO> reviewDTOS = reviews.stream()
                .map(review -> ReviewDTO.toDTO(review))
                .toList();
        return reviewDTOS;
    }

    public ReviewDTO update(ReviewDTO reviewDTO) {
        UserDTO userDTO = userService.findUserById(reviewDTO.getUserid());
        if (userDTO == null) {
            throw new EntityNotFoundException("User with ID " + reviewDTO.getUserid() + " not found");
        }
        User user = UserDTO.toEntity(userDTO);

        HouseDTO houseDTO = houseService.findHouseById(reviewDTO.getHouseid());
        if (houseDTO == null) {
            throw new EntityNotFoundException("House with ID " + reviewDTO.getHouseid() + " not found");
        }

        NeighborhoodDTO neighborhoodDTO = neighborhoodService.findNeighborhoodByName(houseDTO.getNeighborhood());
        if(neighborhoodDTO == null) {
            throw new EntityNotFoundException("Neighborhood with name " + houseDTO.getNeighborhood() + " not found");
        }
        Neighborhood neighborhood = NeighborhoodDTO.toEntity(neighborhoodDTO);
        House house = HouseDTO.toEntity(houseDTO, neighborhood);

        Review updatedReview = reviewRepository.update(ReviewDTO.toEntity(reviewDTO, user, house));
        ReviewDTO updatedReviewDTO = ReviewDTO.toDTO(updatedReview);
        return updatedReviewDTO;
    }

    public void delete(Long id) {
        reviewRepository.delete(id);
    }
}
