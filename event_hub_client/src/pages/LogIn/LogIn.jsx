import React, { useState } from 'react';
import axios from 'axios';
import styles from './LogIn.module.css';
import { Link, Navigate } from 'react-router-dom';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import {Button, Checkbox, Form, Input, message} from 'antd';



const LogIn = () => {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [navigate, setNavigate] = useState(false);
    const onFinish = async (e) => {
        try {
            const response = await axios.post('http://localhost:3001/login', {
                email: e.email,
                password: e.password
            }, {withCredentials: true});
            console.log(response.data);
            message.success('Registration successful!');
            setNavigate(true);
        } catch (err) {
            if (!err.response) {
                // Помилка з'єднання з сервером
                message.error('No server response');
            } else {
                const status = err.response.status;
                switch (status) {
                    case 400:
                        // Помилка валідації даних на сервері
                        message.error('Invalid data. Please check your input.');
                        break;
                    case 401:
                        // Користувач не авторизований
                        message.error('Unauthorized: Please check your credentials.');
                        break;
                    case 403:
                        // Доступ заборонено
                        message.error('Forbidden: You do not have permission to access this resource.');
                        break;
                    case 404:
                        // URL не знайдено
                        message.error('Not Found: The requested resource was not found.');
                        break;
                    case 409:
                        // Конфлікт
                        message.error('Conflict: The resource already exists.');
                        break;
                    case 422:
                        // Невірні вхідні дані
                        message.error('Unprocessable Entity: The request was well-formed but unable to be followed due to semantic errors.');
                        break;
                    case 500:
                        // Внутрішня помилка сервера
                        message.error('Internal Server Error: Something went wrong on the server.');
                        break;
                    default:
                        // Інші типи помилок
                        console.error(err);
                        message.error('Registration failed: ' + err.response.data);
                        break;
                }
            }

        }


        // const { data } = await axios.post('/login', {
        //     email, password
        // }, { withCredentials: true });
        // console.log(data)
        //
        // axios.defaults.headers.common['Authorization'] = `Bearer ${data['token']}`;

        // setNavigate(true);
    };

    if (navigate) {
        return <Navigate to="/"/>;
    }



    return (
        <div className={styles.container}>
            {/*<img className={styles.Photo} src="/images/EventHub_loginPhoto.jpg" alt="Your Photo"/>*/}
            <Form
                name="normal_login"
                className="login-form"
                initialValues={{
                    remember: true,
                }}
                onFinish={onFinish}
            >
                <h1>Login</h1>
                <Form.Item
                    name="email"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your email!',
                        },
                    ]}
                >
                    <Input prefix={<UserOutlined className="site-form-item-icon" />}
                           onChange={e => setEmail(e.target.value)}
                           placeholder="Email" />
                </Form.Item>
                <Form.Item
                    name="password"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your Password!',
                        },
                    ]}
                >
                    <Input.Password
                        prefix={<LockOutlined className="site-form-item-icon" />}
                        onChange={e => setPassword(e.target.value)}
                        type="password"
                        placeholder="Password"
                    />
                </Form.Item>
                <Form.Item>
                    <div className={styles.RememberMe}>
                        <Form.Item name="remember" valuePropName="checked" noStyle>
                            <Checkbox>Remember me</Checkbox>
                        </Form.Item>
                        <a className="login-form-forgot" href="">
                            Forgot password
                        </a>
                    </div>
                </Form.Item>
                <Form.Item style={{ marginBottom: "0px" }}>
                    <Button type="primary" htmlType="submit" className={styles.loginButton}>
                        Login
                    </Button>
                </Form.Item>
                <p style={{ textAlign: "center" }}>Or</p>
                <Form.Item style={{ marginBottom: "0px" }}>
                    <div className={styles.loginButtonContainer}>
                        <Button type="link" htmlType="button" className={styles.socialMediaLogin}>
                            <img className={styles.loginImg} src="/images/fb_logo.png"
                                 alt="Continue with Facebook" />
                        </Button>
                        <Button type="link" htmlType="button" className={styles.socialMediaLogin}>
                            <img className={styles.loginImg} src="/images/apple_logo.png"
                                 alt="Continue with Apple" />
                        </Button>
                        <Button type="link" htmlType="button" className={styles.socialMediaLogin}>
                            <img className={styles.loginImg} src="/images/google_logo.png"
                                 alt="Continue with Google" />
                        </Button>
                    </div>
                </Form.Item>
                <p style={{ textAlign: "center", fontSize: "12px" }}>Don’t have an account in EventHub yet? <Link to="/register">Register!</Link></p>
            </Form>
        </div>

    );
};
export default LogIn;