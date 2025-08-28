import React from 'react';
import RankingColumn from './RankingColumn';
import { RankingItemProps } from './RankingItem'; // 데이터 타입을 위해 import
import styles from './HotRankings.module.scss'; // module.scss 임포트

// mockData 배열이 RankingItemProps 타입을 따르도록 명시합니다.
const mockData: RankingItemProps[] = [
  {
    rank: 1,
    title: '뮤지컬 <베르사유의 장미>',
    location: '충무아트센터 대극장',
    dateRange: '2024.7.16 - 10.13',
    subDetail: '인스파이어 아레나',
    imageUrl: null,
  },
  // 스크린샷과 동일하게 5개를 더 추가합니다.
  {
    rank: 2,
    title: '뮤지컬 <베르사유의 장미>',
    location: '충무아트센터 대극장',
    dateRange: '2024.7.16 - 10.13',
    subDetail: '인스파이어 아레나',
    imageUrl: null,
  },
  {
    rank: 3,
    title: '뮤지컬 <베르사유의 장미>',
    location: '충무아트센터 대극장',
    dateRange: '2024.7.16 - 10.13',
    subDetail: '인스파이어 아레나',
    imageUrl: null,
  },
  {
    rank: 4,
    title: '뮤지컬 <베르사유의 장미>',
    location: '충무아트센터 대극장',
    dateRange: '2024.7.16 - 10.13',
    subDetail: '인스파이어 아레나',
    imageUrl: null,
  },
  {
    rank: 5,
    title: '뮤지컬 <베르사유의 장미>',
    location: '충무아트센터 대극장',
    dateRange: '2024.7.16 - 10.13',
    subDetail: '인스파이어 아레나',
    imageUrl: null,
  },
  {
    rank: 6,
    title: '뮤지컬 <베르사유의 장미>',
    location: '충무아트센터 대극장',
    dateRange: '2024.7.16 - 10.13',
    subDetail: '인스파이어 아레나',
    imageUrl: null,
  },
];

const HotRankings: React.FC = () => {
  return (
    // className을 styles 객체에서 가져와 적용합니다.
    <div className={styles.container}>
      <RankingColumn title="핫랭킹" items={mockData} />
      <RankingColumn title="핫랭킹" items={mockData} />
      <RankingColumn title="핫랭킹" items={mockData} />
    </div>
  );
};

export default HotRankings;
