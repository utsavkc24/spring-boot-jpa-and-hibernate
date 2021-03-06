package com.in28minutes.jpa.hibernate.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.persistence.EntityManager;

import com.in28minutes.jpa.hibernate.demo.DemoApplication;
import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.entity.Review;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = DemoApplication.class)
class CourseRepositoryTest {
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Autowired
	CourseRepository courseRepository;

	@Autowired
	EntityManager entityManager;

	@Test
	void contextLoads() {
		LOGGER.info("Test is running");
	}

	@Test
	void findById_basic() {
		Course course = courseRepository.findById(10001L);
		assertEquals("History", course.getName());
	}

	// We will perform soft delete functionality on this method.
	@Test
	@DirtiesContext
	void deleteById_basic() {
		courseRepository.deleteById(10002L);
		assertNull(courseRepository.findById(10002L));
	}

	@Test
	@DirtiesContext
	void save_basic() {
		// get a course
		Course course = courseRepository.findById(10001L);

		// update the course
		course.setName("History - Updated");
		courseRepository.save(course);

		// check
		Course course1 = courseRepository.findById(10001L);
		assertEquals("History - Updated", course1.getName());
	}

	@Test
	@Transactional
	public void retrieveReviewFromCourse() {
		Course course = courseRepository.findById(10001L);
		LOGGER.info("{} ", course.getReviews());
	}

	@Test
	// @Transactional
	public void retrieveCourseFromReview() {
		Review review = entityManager.find(Review.class, 50001L);
		LOGGER.info("{} ", review.getCourse());
	}
}
