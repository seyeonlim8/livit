package lim.seyeon.safe.stay.presentation.Controller;

import jakarta.validation.Valid;
import lim.seyeon.safe.stay.application.ReviewService;
import lim.seyeon.safe.stay.presentation.DTO.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(value = "/reviews")
    public ReviewDTO createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        return reviewService.add(reviewDTO);
    }

    @GetMapping(value = "/reviews/{id}")
    public ReviewDTO findReviewById(@PathVariable Long id) {
        return reviewService.findReviewById(id);
    }

    @GetMapping(value = "/reviews")
    public List<ReviewDTO> findReviews(@RequestParam(required = false) Long userid,
                                       @RequestParam(required = false) Long houseid) {
        if(userid != null) {
            return reviewService.findReviewsByUserId(userid);
        } else if(houseid != null) {
            return reviewService.findReviewsByHouseId(houseid);
        }
        return reviewService.findAll();
    }

    @PutMapping(value = "/reviews/{id}")
    public ReviewDTO update(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        reviewDTO.setId(id);
        return reviewService.update(reviewDTO);
    }

    @DeleteMapping(value = "/reviews/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.delete(id);
    }
}
