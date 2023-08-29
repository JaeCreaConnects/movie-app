import React, { useState } from 'react'
import '../../css/Login.css'

const UserLogin = () => {
    const [username, setUserName] = useState()
    const [password, setPassword] = useState()
    return (
        <div className="login-wrapper">
            <h1 className="login-header">Please Log In</h1>
            <form className="login-form">
                <label>
                    <p>Username</p>
                    <input
                        type="text"
                        required={true}
                        onChange={(e) => setUserName(e.target.value)}
                    />
                </label>
                <label>
                    <p>Password</p>
                    <input
                        required={true}
                        type="password"
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </label>
                <div>
                    <button type="submit">Submit</button>
                </div>
            </form>
        </div>
    )
}
export default UserLogin
