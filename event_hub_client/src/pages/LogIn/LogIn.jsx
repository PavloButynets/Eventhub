import React from 'react';
import axios from 'axios';
import styles from './LogIn.module.css';
import { Link } from 'react-router-dom';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Checkbox, Form, Input } from 'antd';
const LogIn = () => {
    const onFinish = async (values) => {
        try {
            const response = await axios.post('http://localhost:3001/users', {
                email: values.email,
                password: values.password
            });
            console.log(response.data); // Assuming your server responds with user data upon successful login
            // Redirect the user to the dashboard or perform any other necessary actions upon successful login
        } catch (error) {
            console.error('Login failed:', error);
            // Handle login failure, e.g., show error message to the user
        }
    };




    return (

        <div className={styles.bodyC}>
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
                        <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="Email"/>
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
                            prefix={<LockOutlined className="site-form-item-icon"/>}

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


                    <Form.Item style={{marginBottom: "0px"}}>
                        <Button type="primary" htmlType="submit" className={styles.loginButton}>
                            Login
                        </Button>

                    </Form.Item>
                    <p style={{textAlign: "center"}}>Or</p>


                    <Form.Item style={{marginBottom: "0px"}}>
                        <div className={styles.loginButtonContainer}>

                            <Button type="link" htmlType="button" className={styles.socialMediaLogin}>
                                <img className={styles.loginImg} src="/images/fb_logo.png"
                                     alt="Continue with Facebook"/>
                            </Button>


                            <Button type="link" htmlType="button" className={styles.socialMediaLogin}>
                                <img className={styles.loginImg} src="/images/apple_logo.png"
                                     alt="Continue with Apple"/>
                            </Button>


                            <Button type="link" htmlType="button" className={styles.socialMediaLogin}>
                                <img className={styles.loginImg} src="/images/google_logo.png"
                                     alt="Continue with Google"/>
                            </Button>
                        </div>

                    </Form.Item>
                    <p style={{textAlign: "center", fontSize:"12px"}}>Don’t have an account in EventHub yet? <a href="/register">Register!</a></p>

                </Form>
            </div>
        </div>
    );
};
export default LogIn;