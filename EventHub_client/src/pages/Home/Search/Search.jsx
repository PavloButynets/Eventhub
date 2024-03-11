import React, { useState } from 'react';
import { SearchOutlined } from '@ant-design/icons';
import styles from './../../../components/componentsStyles/searchContainer.module.css';
import customStyles from './CustomSearch.module.css';

const SearchInput = () => {
  const [searchValue, setSearchValue] = useState('');

  const handleSearch = (event) => {
    event.preventDefault(); 
    console.log('Виконано пошук для:', searchValue);
  };

  const handleInputChange = (event) => {
    setSearchValue(event.target.value);
  };

  return (
    <div className={customStyles.SearchInputcontainer}>
      <form className={styles.searchForm} onSubmit={handleSearch}>
        <input
          type="text"
          className={styles.searchInput}
          placeholder="Search..."
          value={searchValue}
          onChange={handleInputChange}
        />
        <button type="submit" className={styles.searchButton}>
          <SearchOutlined className={styles.searchIcon} />
        </button>
      </form>
    </div>
  );
};

export default SearchInput;
