import React, { useState } from 'react';
import styles from './Header.module.scss';
import { Link } from 'react-router-dom';

const Header: React.FC = () => {
  const [searchTerm, setSearchTerm] = useState('');

  const handleClearSearch = () => {
    setSearchTerm('');
  };

  return (
    <header className={styles.header}>
      {/* 로고 */}
      <div className={styles.logo}>
        {/* 실제 로고 아이콘/이미지로 교체하세요. */}
        ⚛️
      </div>

      {/* 검색창 */}
      <div className={styles.searchContainer}>
        <input
          type="text"
          className={styles.searchInput}
          placeholder="검색"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        {searchTerm && (
          <button onClick={handleClearSearch} className={styles.clearButton}>
            ×
          </button>
        )}
      </div>

      {/* 오른쪽 섹션 */}
      <div className={styles.rightSection}>
        <nav className={styles.navLinks}>
          <a href="#ticket">Ticket</a>
          <a href="#review">Review</a>
          <a href="#mypage">My Page</a>
        </nav>

        <div className={styles.userActions}>
          {/* 실제 아이콘으로 교체하세요. */}
          <span className={styles.notificationBell}>🔔</span>
          <Link to="/login">
            <button className={`${styles.button} ${styles.signIn}`}>
              Sign in
            </button>
          </Link>

          <Link to="/signup">
            <button className={`${styles.button} ${styles.register}`}>
              Register
            </button>
          </Link>
        </div>
      </div>
    </header>
  );
};

export default Header;
