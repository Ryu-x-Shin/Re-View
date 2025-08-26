import React, { useState } from 'react';
import RankingItem, { RankingItemProps } from './RankingItem'; // RankingItemProps 타입을 import
import styles from './RankingColumn.module.scss'; // module.scss 임포트

// RankingColumn 컴포넌트가 받을 props의 타입을 정의합니다.
interface RankingColumnProps {
  title: string;
  items: RankingItemProps[]; // RankingItemProps 객체로 이루어진 배열
}

const RankingColumn: React.FC<RankingColumnProps> = ({ title, items }) => {
  // useState의 상태값 타입을 명시적으로 선언합니다.
  const [activeTab, setActiveTab] = useState<string>('콘서트');
  const tabs: string[] = ['영화', '콘서트', '전시', '연극'];

  return (
    <div className={styles.column}>
      <div className={styles.header}>
        <h2>{title}</h2>
        <a href="#see-more" className={styles.seeMore}>
          더보기 &gt;
        </a>
      </div>

      <div className={styles.tabs}>
        {tabs.map((tab) => (
          <button
            key={tab}
            // 조건부 클래스 적용: 기본 클래스와 active 클래스를 함께 사용
            className={`${styles.tabButton} ${activeTab === tab ? styles.active : ''}`}
            onClick={() => setActiveTab(tab)}
          >
            {tab}
          </button>
        ))}
      </div>

      <div>
        {items.map((item, index) => (
          <RankingItem key={`${item.title}-${index}`} {...item} />
        ))}
      </div>
    </div>
  );
};

export default RankingColumn;
