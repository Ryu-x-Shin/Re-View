import Header from '../../features/home/components/Header/Header';
import SpecialOffers from '../../features/home/components/HotDeal/SpecialOffers';
import HotRankings from '../../features/home/components/HotRanking/HotRankings';
import BestReviews from '../../features/home/components/Review/BestReviews';

const Home = () => {
  return (
    // <ul>
    //   <li>
    //     <Link to="/login">로그인</Link>
    //   </li>
    //   <li>
    //     <Link to="/signup">회원가입</Link>
    //   </li>
    // </ul>
    <div>
      <Header />
      <HotRankings />

      <SpecialOffers />

      <BestReviews />
    </div>
  );
};

export default Home;
