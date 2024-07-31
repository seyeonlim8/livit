package lim.seyeon.safe.stay.presentation.Controller.View;

import lim.seyeon.safe.stay.application.HouseService;
import lim.seyeon.safe.stay.application.ReviewService;
import lim.seyeon.safe.stay.domain.User.UserService;
import lim.seyeon.safe.stay.presentation.DTO.HouseDTO;
import lim.seyeon.safe.stay.presentation.Filter.HouseFilter;
import lim.seyeon.safe.stay.presentation.DTO.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/view-houses")
public class ViewHousesController {

    private static final Logger logger = LoggerFactory.getLogger(ViewHousesController.class);
    private final ReviewService reviewService;

    private HouseService houseService;
    private UserDetailsService userDetailsService;
    private UserService userService;

    @Autowired
    public ViewHousesController(HouseService houseService, UserDetailsService userDetailsService, ReviewService reviewService, UserService userService) {
        this.houseService = houseService;
        this.userDetailsService = userDetailsService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping
    public String viewHouses(@RequestParam(required = false) String name,
                             @RequestParam(required = false) String neighborhood,
                             @RequestParam(required = false) Integer minPrice,
                             @RequestParam(required = false) Integer maxPrice,
                             @RequestParam(required = false) String sort,
                             Model model,
                             Principal principal) {
        HouseFilter filter = new HouseFilter();
        filter.setName(name);
        filter.setNeighborhood(neighborhood);
        filter.setMinPrice(minPrice);
        filter.setMaxPrice(maxPrice);
        filter.setSort(sort);

        List<HouseDTO> houses = houseService.findHouses(filter);
        model.addAttribute("houses", houses);

        if (principal != null) {
            String username = principal.getName();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            model.addAttribute("userdetail", userDetails);
        } else {
            logger.debug("Principal is null");
        }

        return "view-houses";
    }

    @GetMapping("/{houseid}")
    public String viewHouse(@PathVariable Long houseid, Model model, Principal principal) {
        String username = principal.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        model.addAttribute("userdetail", userDetails);

        HouseDTO houseDTO = houseService.findHouseById(houseid);
        model.addAttribute("house", houseDTO);

        List<ReviewDTO> reviewDTOS = reviewService.findReviewsByHouseId(houseid);
        Map<Long, String> reviewers = new HashMap<>();
        for (ReviewDTO reviewDTO : reviewDTOS) {
            Long reviewId = reviewDTO.getId();
            String reviewerName = userService.findUserById(reviewDTO.getUserid()).getUsername();
            reviewers.put(reviewId, reviewerName);
        }
        model.addAttribute("reviews", reviewDTOS);
        model.addAttribute("reviewers", reviewers);

        return "house-details";
    }

}
