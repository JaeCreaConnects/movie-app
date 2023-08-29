import React, { useState } from 'react'

const UserRegister = () => {
    const [firstName, setFirstName] = useState()
    const [lastName, setLastName] = useState()
    const [userName, setUserName] = useState()
    const [password, setPassword] = useState()
    const [email, setEmail] = useState()

    return (
        <div className="register-wrapper">
            <h1>Please Log In</h1>
            <form>
                <label>
                    <p>First Name</p>
                    <input
                        type="firstName"
                        onChange={(e) => setFirstName(e.target.value)}
                    />
                </label>
                <label>
                    <p>Last Name</p>
                    <input
                        type="lastName"
                        onChange={(e) => setLastName(e.target.value)}
                    />
                </label>
                <label>
                    <p>Username</p>
                    <input
                        type="text"
                        onChange={(e) => setUserName(e.target.value)}
                    />
                </label>
                <label>
                    <p>Password</p>
                    <input
                        type="password"
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </label>
                <label>
                    <p>Email</p>
                    <input
                        type="email"
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </label>
                <div>
                    <button type="submit">Submit</button>
                </div>
            </form>
        </div>
    )
}
export default UserRegister
