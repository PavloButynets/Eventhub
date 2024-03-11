import React, { useState } from 'react';
import { Menu, Dropdown, Select, Input, DatePicker } from 'antd';
import { CalendarOutlined } from '@ant-design/icons';
import { RoundButton } from '../../../components/Buttons/RoundButton/roundButton';
import styles from './MyEvents.module.css'; 


const MyEvents = () => {

    return (
        <div className={styles.filterContainer}>
            <div className={styles.filterButtonContainer}>
                <RoundButton icon={<CalendarOutlined />} />
            </div>
        </div>
    );
};

export default MyEvents;
