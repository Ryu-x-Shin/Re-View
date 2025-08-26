import React from 'react';
import DealCard, { DealCardProps } from './DealCard';
import styles from './SpecialOffers.module.scss';

// 실제 앱에서는 API를 통해 받아올 데이터입니다.
const mockData: DealCardProps[] = [
  {
    id: 1,
    remainingTime: '00:18:05',
    title: '뮤지컬 <베르사유의 장미>',
    category: '문화관',
    dateRange: '2024.10.19~10.02',
    discount: 47,
    originalPrice: 40000,
    finalPrice: 21200,
  },
  {
    id: 2,
    remainingTime: '00:18:05',
    title: '뮤지컬 <베르사유의 장미>',
    category: '문화관',
    dateRange: '2024.10.19~10.02',
    discount: 47,
    originalPrice: 40000,
    finalPrice: 21200,
  },
  {
    id: 3,
    remainingTime: '00:18:05',
    title: '뮤지컬 <베르사유의 장미>',
    category: '문화관',
    dateRange: '2024.10.19~10.02',
    discount: 47,
    originalPrice: 40000,
    finalPrice: 21200,
  },
  {
    id: 4,
    remainingTime: '00:18:05',
    title: '뮤지컬 <베르사유의 장미>',
    category: '문화관',
    dateRange: '2024.10.19~10.02',
    discount: 47,
    originalPrice: 40000,
    finalPrice: 21200,
  },
  {
    id: 5,
    remainingTime: '00:18:05',
    title: '뮤지컬 <베르사유의 장미>',
    category: '문화관',
    dateRange: '2024.10.19~10.02',
    discount: 47,
    originalPrice: 40000,
    finalPrice: 21200,
  },
];

const SpecialOffers: React.FC = () => {
  return (
    <div className={styles.container}>
      {mockData.map((deal) => (
        <DealCard key={deal.id} {...deal} />
      ))}
    </div>
  );
};

export default SpecialOffers;
