import React from 'react';
import styles from './DealCard.module.scss';

// 카드가 받을 데이터의 타입을 정의합니다.
export interface DealCardProps {
  id: number;
  imageUrl?: string | null;
  remainingTime: string;
  title: string;
  category: string;
  dateRange: string;
  discount: number;
  originalPrice: number;
  finalPrice: number;
}

const DealCard: React.FC<DealCardProps> = ({
  imageUrl,
  remainingTime,
  title,
  category,
  dateRange,
  discount,
  originalPrice,
  finalPrice,
}) => {
  // 가격을 콤마가 있는 형식으로 변환하는 함수
  const formatPrice = (price: number) => price.toLocaleString('ko-KR');

  return (
    <div className={styles.card}>
      <div className={styles.imageContainer}>
        {/* 이미지가 있다면 아래 주석을 해제하고 사용합니다. */}
        {/* {imageUrl && <img src={imageUrl} alt={title} className={styles.image} />} */}
        <div className={styles.timeBadge}>
          <span>남은시간</span>
          <span>{remainingTime}</span>
        </div>
      </div>
      <div className={styles.info}>
        <h3 className={styles.title}>{title}</h3>
        <p className={styles.category}>{category}</p>
        <p className={styles.date}>{dateRange}</p>
      </div>
      <div className={styles.priceSection}>
        <span className={styles.discount}>{discount}%</span>
        <span className={styles.originalPrice}>
          {formatPrice(originalPrice)}
        </span>
        <span className={styles.finalPrice}>{formatPrice(finalPrice)}</span>
      </div>
    </div>
  );
};

export default DealCard;
