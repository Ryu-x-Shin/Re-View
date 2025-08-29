import React from 'react';
import styles from './ReviewCard.module.scss';

// ReviewCard가 받을 props 타입 정의
export interface ReviewCardProps {
  id: number;
  itemTitle: string;
  reviewTitle: string;
  reviewBody: string;
  rating: number; // 0-5 사이의 숫자
  userAvatarUrl?: string | null;
  userName: string;
  imageUrl?: string | null;
}

const ReviewCard: React.FC<ReviewCardProps> = ({
  itemTitle,
  reviewTitle,
  reviewBody,
  rating,
  userAvatarUrl,
  userName,
  imageUrl,
}) => {
  // 별점을 렌더링하는 함수
  const renderStars = () => {
    const stars = [];
    for (let i = 1; i <= 5; i++) {
      stars.push(
        <span
          key={i}
          className={`${styles.star} ${i > rating ? styles.emptyStar : ''}`}
        >
          ★
        </span>,
      );
    }
    return stars;
  };

  return (
    <div className={styles.card}>
      <div className={styles.content}>
        <span className={styles.itemTitle}>{itemTitle}</span>
        <h3 className={styles.reviewTitle}>{reviewTitle}</h3>
        <p className={styles.reviewBody}>{reviewBody}</p>
        <div className={styles.rating}>{renderStars()}</div>
        <div className={styles.userInfo}>
          <div
            className={styles.avatar}
            style={{ backgroundImage: `url(${userAvatarUrl})` }}
          />
          <span className={styles.userName}>{userName}</span>
        </div>
      </div>
      <div className={styles.imageContainer}>
        {imageUrl ? <img src={imageUrl} alt="Review" /> : '🖼️'}
      </div>
    </div>
  );
};

export default ReviewCard;