import React, { useEffect, useRef } from 'react'
import { useParams } from 'react-router-dom'
import { Container, Row, Col } from 'react-bootstrap'
import axios from 'axios'

import ReviewForm from './ReviewForm'

const Reviews = ({ getMovieData, movie, reviews, setReviews }) => {
    // So, in summary, useRef is being used here to facilitate the interaction with an HTML input element used for entering
    // and submitting reviews in the Reviews component.
    //this captures the input element
    const revText = useRef()
    const params = useParams()
    const movieId = params.movieId

    useEffect(() => {
        getMovieData(movieId)
    }, [])

    const addReview = async (e) => {
        e.preventDefault()

        const rev = revText.current

        try {
            // TODO: pull this out to own api file
            await axios.post('http://localhost:8080/api/v1/reviews', {
                reviewBody: rev.value,
                imdbId: movieId,
            })
            const updatedReviews = [...reviews, { body: rev.value }]

            rev.value = ''

            setReviews(updatedReviews)
        } catch (err) {
            console.error(err)
        }
    }

    return (
        <Container>
            <Row>
                <Col>
                    <h3>Reviews</h3>
                </Col>
            </Row>
            <Row className="mt-2">
                <Col>
                    <img src={movie?.poster} alt="" />
                </Col>
                <Col>
                    {
                        <>
                            <Row>
                                <Col>
                                    <ReviewForm
                                        handleSubmit={addReview}
                                        revText={revText}
                                        labelText="Write a Review?"
                                    />
                                </Col>
                            </Row>

                            <Row>
                                <Col>
                                    <hr />
                                </Col>
                            </Row>
                        </>
                    }
                    {reviews?.map((review) => {
                        return (
                            <>
                                <Row>
                                    <Col>{review.body}</Col>
                                </Row>
                                <Row>
                                    <Col>
                                        <hr />
                                    </Col>
                                </Row>
                            </>
                        )
                    })}
                </Col>
            </Row>
            <Row>
                <Col>
                    <hr />
                </Col>
            </Row>
        </Container>
    )
}
export default Reviews
