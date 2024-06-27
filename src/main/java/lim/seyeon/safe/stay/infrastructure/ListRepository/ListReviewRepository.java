package lim.seyeon.safe.stay.infrastructure.ListRepository;

/*
@Repository
public class ListReviewRepository {

    private List<Review> reviews = new CopyOnWriteArrayList<>();
    private AtomicLong index = new AtomicLong(1L);

    public Review add(Review review) {
        review.setId(index.getAndIncrement());
        reviews.add(review);
        return review;
    }

    public Review findReviewById(Long id) {
        return reviews.stream()
                .filter(review -> review.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Review with id" + id + " not found"));
    }

    public List<Review> findAll() {
        return reviews;
    }

    public List<Review> findReviewsByUserid(Long userid) {
        return reviews.stream()
                .filter(review -> review.getUser().getId().equals(userid))
                .toList();
    }

    public List<Review> findReviewsByHouseid(Long houseid) {
        return reviews.stream()
                .filter(review -> review.getUser().getId().equals(houseid))
                .toList();
    }

    public Review update(Review review) {
        Integer indexToUpdate = reviews.indexOf(review);
        reviews.set(indexToUpdate, review);
        return review;
    }

    public void delete(Long id) {
        Review reviewToDelete = findReviewById(id);
        reviews.remove(reviewToDelete);
    }
}

*/