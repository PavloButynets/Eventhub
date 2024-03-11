import React, { useState } from 'react';
import { Menu, Dropdown, Select, Input, DatePicker } from 'antd';
import { FilterOutlined } from '@ant-design/icons';
import { RoundButton } from '../../../components/Buttons/RoundButton/roundButton';
import styles from './Filter.module.css';
import PrimaryButton from '../../../components/Buttons/PrimaryButton/PrimaryButton';
import '../../../App.css'
const { Option } = Select;

const EventFilter = () => {
  const [isOpen, setIsOpen] = useState(false);


  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  const handleApplyButtonClick = () => {
    console.log('Apply button clicked');
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
                style={{ width: '100%' }}
              >
                <Option value="sport">Sport</Option>
                <Option value="art">Art</Option>
                {/* Додати більше типів подій */}
              </Select>
            </Menu.Item>
            <Menu.Item key="2">
              <Input.Group compact>
                <Input placeholder="Min Participants" style={{ width: '50%' }} />
                <Input placeholder="Max Participants" style={{ width: '50%' }} />
              </Input.Group>
            </Menu.Item>
            <Menu.Item key="3">
              <Input placeholder="Location" />
            </Menu.Item>
            <Menu.Item key="4">
              <DatePicker.RangePicker placeholder={['Start Date', 'End Date']} />
            </Menu.Item>
            <div className={styles.applyButtonContainer}>
              <PrimaryButton onClick={handleApplyButtonClick}>Apply</PrimaryButton>
            </div>
          </Menu>
        }
        trigger={['click']}
        visible={isOpen}
        onVisibleChange={toggleMenu}
      >
        <div className={styles.filterButtonContainer}>
          <RoundButton icon={<FilterOutlined />} onClick={toggleMenu} />
        </div>
      </Dropdown>
    </div>
  );
};

export default EventFilter;
