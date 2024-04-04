import React, { useState, useRef, useEffect } from 'react';
import styles from './Search.module.css';
import SearchInput from './SearchInput';
import ListEvents from '../../../components/ListEvents/ListEvents'; 
import { getEventsDataSearch } from '../../../api/getEventsData';
import {  CloseOutlined } from '@ant-design/icons';
import { useSearchParams } from 'react-router-dom';

const SearchEvents = () => {
  const [searchValue, setSearchValue] = useState('');
  const [showResults, setShowResults] = useState(false);
  const [eventsData, setEventsData] = useState([]); 
  const [searchParams, setSearchParams] = useSearchParams();
  

  const handleSearch = async (event) => {
    event.preventDefault();
    // Перевірка, чи не є searchValue порожнім рядком(враховуючи пробіли)
    if (searchValue.trim() !== '') {
      try {
        const data = await getEventsDataSearch(searchValue);
        setEventsData(data);
        setSearchParams({ search: searchValue});
        setShowResults(true);
      } catch(error) {
        console.error('Error getting events data:', error);
      }
    }
  };
  
  const handleInputChange = (event) => {
    setSearchValue(event.target.value);
  };
  const handleClearClick = () => {
    setSearchValue('');
    setSearchParams({});
    setShowResults(false);
  };
  
  useEffect(() => {
    if (!showResults) {
      setSearchParams({});
    }
  }, [showResults]);


  return (
    <div className={`${styles.SearchContainer} ${showResults ? styles.active : styles.inactive}`}>
      <div className={styles.SearchInput} >
        <SearchInput
          searchValue={searchValue}
          handleInputChange={handleInputChange}
          handleSearch={handleSearch}
          showResults = {showResults}
          handleClearButtonClick = {handleClearClick}
        />
      </div>
      
      {showResults ? (( 
        <div className={styles.ResultsContainer}>
          {eventsData.length === 0 ? (
            <p className={styles.NoResultsText}>No results found for "{searchValue}"</p>
          ) : (
            <ListEvents eventsData={eventsData}/>
          )}
      </div>
      )) : null}
    </div>
  );
};

export default SearchEvents;
