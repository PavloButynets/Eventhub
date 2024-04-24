import React, { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { Menu, Dropdown, Select, Input, DatePicker } from "antd";
import { FilterOutlined } from "@ant-design/icons";
import { RoundButton } from "../../../components/Buttons/RoundButton/roundButton";
import styles from "./Filter.module.css";
import PrimaryButton from "../../../components/Buttons/PrimaryButton/PrimaryButton";
import FilteredEvents from "./FilteredEvents";
import { getCategories } from "../../../api/getCategories";
import { getFilteredEvents } from "../../../api/getFilteredEvents";
import "../../../App.css";
const { Option } = Select;

const EventFilter = () => {
  const [searchParams, setSearchParams] = useSearchParams("");
  const [isOpen, setIsOpen] = useState(false);
  const [eventsData, setEventsData] = useState(null);

  const [categories, setCategories] = useState([]);
  const [minParticipants, setMinParticipants] = useState();
  const [maxParticipants, setMaxParticipants] = useState();
  const [location, setLocation] = useState();
  const [dateRange, setDateRange] = useState([null, null]);

  const [categoryOptions, setCategoryOptions] = useState([]);
  useEffect(() => {
    async function fetchCategories() {
      try {
        const data = await getCategories();
        const options = data.map((item) => (
          <Option key={item.id} value={item.name}>
            {item.name}
          </Option>
        ));
        setCategoryOptions(options);
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    }
    fetchCategories();
  }, []);

  const fetchData = async () => {
    try {
      const events = await getFilteredEvents();
      setEventsData(events);
    } catch (error) {
      console.log("Error fetching filtered events", error);
    }
  };

  useEffect(() => {
    if (!eventsData) {
      setSearchParams("");
    }
  }, [eventsData]);

  const resetFilter = () => {
    setCategories([]);
    setMinParticipants();
    setMaxParticipants();
    setLocation();
    setDateRange([null, null]);
  };

  const handleClose = () => {
    setEventsData(null);
    setSearchParams("");
  };

  const toggleMenu = () => {
    resetFilter();
    setIsOpen(!isOpen);
  };

  const handleApplyButtonClick = async () => {
    const filterData = {
      filter: true,
      min_participants: minParticipants || 0,
      max_participants: maxParticipants || 0,
      start_at: dateRange[0] || "",
      expire_at: dateRange[1] || "",
      location: location || "",
      category_requests: categories,
    };
    setSearchParams(filterData);
    fetchData();
    toggleMenu();
  };

  return (
    <div>
      <Dropdown
        overlay={
          <Menu className={styles.filterContainer}>
            <Menu.Item key="1">
              <Select
                mode="multiple"
                placeholder="Select event types"
                style={{ width: "100%" }}
                value={categories}
                onChange={(category) => setCategories(category)}
              >
                {categoryOptions}
              </Select>
            </Menu.Item>
            <Menu.Item key="2">
              <Input.Group compact>
                <Input
                  placeholder="Min Participants"
                  style={{ width: "50%" }}
                  onChange={(e) => setMinParticipants(e.target.value)}
                  value={minParticipants}
                />
                <Input
                  placeholder="Max Participants"
                  style={{ width: "50%" }}
                  onChange={(e) => setMaxParticipants(e.target.value)}
                  value={maxParticipants}
                />
              </Input.Group>
            </Menu.Item>
            <Menu.Item key="3">
              <Input
                placeholder="Location"
                onChange={(e) => setLocation(e.target.value)}
                value={location}
              />
            </Menu.Item>
            <Menu.Item key="4">
              <DatePicker.RangePicker
                placeholder={["Start Date", "End Date"]}
                onChange={(dates) => setDateRange(dates)}
                value={dateRange}
              ></DatePicker.RangePicker>
            </Menu.Item>
            <div className={styles.applyButtonContainer}>
              <PrimaryButton onClick={handleApplyButtonClick}>
                Apply
              </PrimaryButton>
            </div>
          </Menu>
        }
        trigger={["click"]}
        visible={isOpen}
        onVisibleChange={toggleMenu}
      >
        <div className={styles.filterButtonContainer}>
          <RoundButton icon={<FilterOutlined />} onClick={toggleMenu} />
        </div>
      </Dropdown>

      {searchParams.get("filter") && eventsData && (
        <FilteredEvents handleClose={handleClose} eventsData={eventsData} />
      )}
    </div>
  );
};

export default EventFilter;
