import React from 'react';
import ReviewCard, { ReviewCardProps } from './ReviewCard';
import styles from './BestReviews.module.scss';

// API를 통해 받아올 가상 데이터
const mockData: ReviewCardProps[] = [
  {
    id: 1,
    itemTitle: '뮤지컬 <베르사유의 장미>',
    reviewTitle: '캐릭터마다 서사가 있는 것이 너무 좋았어요',
    reviewBody:
      '한명 한명 모두 자신의 히스토리를 가지고 자유의지를 향해 달려가는 세 사람의 이야기가 너무 좋았습니다. 표현의 자유를 억압받던 그 시대에도 자신의 몸을 불사지르며 자유를 외치던 이들도 있는데 현재에 살고 있는 나는 왜 다른 사람들의 눈치를 보며 자유롭지 못한...',
    rating: 3,
    userName: 'imjasu***',
    userAvatarUrl: null, // 실제 이미지 URL을 넣으세요.
    imageUrl: null,
  },
  // 동일한 데이터를 3번 더 추가하여 2x2 그리드를 만듭니다.
  {
    id: 2,
    itemTitle: '뮤지컬 <베르사유의 장미>',
    reviewTitle: '캐릭터마다 서사가 있는 것이 너무 좋았어요',
    reviewBody:
      '한명 한명 모두 자신의 히스토리를 가지고 자유의지를 향해 달려가는 세 사람의 이야기가 너무 좋았습니다. 표현의 자유를 억압받던 그 시대에도 자신의 몸을 불사지르며 자유를 외치던 이들도 있는데 현재에 살고 있는 나는 왜 다른 사람들의 눈치를 보며 자유롭지 못한...',
    rating: 3,
    userName: 'imjasu***',
    userAvatarUrl: null,
    imageUrl: null,
  },
  {
    id: 3,
    itemTitle: '뮤지컬 <베르사유의 장미>',
    reviewTitle: '캐릭터마다 서사가 있는 것이 너무 좋았어요',
    reviewBody:
      '한명 한명 모두 자신의 히스토리를 가지고 자유의지를 향해 달려가는 세 사람의 이야기가 너무 좋았습니다. 표현의 자유를 억압받던 그 시대에도 자신의 몸을 불사지르며 자유를 외치던 이들도 있는데 현재에 살고 있는 나는 왜 다른 사람들의 눈치를 보며 자유롭지 못한...',
    rating: 3,
    userName: 'imjasu***',
    userAvatarUrl: null,
    imageUrl: null,
  },
  {
    id: 4,
    itemTitle: '뮤지컬 <베르사유의 장미>',
    reviewTitle: '캐릭터마다 서사가 있는 것이 너무 좋았어요',
    reviewBody:
      '한명 한명 모두 자신의 히스토리를 가지고 자유의지를 향해 달려가는 세 사람의 이야기가 너무 좋았습니다. 표현의 자유를 억압받던 그 시대에도 자신의 몸을 불사지르며 자유를 외치던 이들도 있는데 현재에 살고 있는 나는 왜 다른 사람들의 눈치를 보며 자유롭지 못한...',
    rating: 3,
    userName: 'imjasu***',
    userAvatarUrl: null,
    imageUrl: null,
  },
];

const BestReviews: React.FC = () => {
  return (
    <section className={styles.container}>
      <h2 className={styles.sectionTitle}>베스트 리뷰</h2>
      <div className={styles.grid}>
        {mockData.map((review) => (
          <ReviewCard key={review.id} {...review} />
        ))}
      </div>
    </section>
  );
};

export default BestReviews;
