import React from 'react';
import styles from './RankingItem.module.scss'; // module.scss 임포트

// RankingItem 컴포넌트가 받을 props의 타입을 정의합니다.
export interface RankingItemProps {
  rank: number;
  imageUrl: string | null; // 이미지 URL은 문자열이거나 없을 수 있습니다.
  title: string;
  location: string;
  dateRange: string;
  subDetail: string;
}

const RankingItem: React.FC<RankingItemProps> = ({
  rank,
  imageUrl,
  title,
  location,
  dateRange,
  subDetail,
}) => {
  return (
    <div className={styles.item}>
      <div className={styles.number}>{rank}</div>
      <div className={styles.imagePlaceholder}>
        {imageUrl && <img src={imageUrl} alt={title} />}
      </div>
      <div className={styles.details}>
        <h3>{title}</h3>
        <p>{location}</p>
        <p className={styles.dateRange}>{dateRange}</p>
        <p className={styles.subDetail}>{subDetail}</p>
      </div>
    </div>
  );
};

export default RankingItem;
