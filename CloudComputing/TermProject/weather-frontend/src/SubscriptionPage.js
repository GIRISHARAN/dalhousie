import React, { useState } from "react";
import axios from "axios";

const SubscriptionPage = () => {
    const [gmail, setGmail] = useState("");
    const [city, setCity] = useState("");
    const [province, setProvince] = useState("");
    const [country, setCountry] = useState("");
    const [message, setMessage] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        const data = {
            gmail: gmail,
            location: `${city}, ${province}, ${country}`,
        };

        try {
            const response = await axios.post("https://0hisgei5dh.execute-api.us-east-1.amazonaws.com/production/subscribe", data);
            if (response.status === 200) {
                setMessage(
                    "Kindly subscribe to the mail received to receive daily weather details for the provided location"
                );
            } else {
                setMessage("Error occurred, kindly try again");
            }
        } catch (error) {
            setMessage("Error occurred, kindly try again");
        }
    };

    const handleReset = () => {
        setGmail("");
        setCity("");
        setProvince("");
        setCountry("");
        setMessage("");
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="gmail">Gmail:</label>
                    <input
                        type="email"
                        id="gmail"
                        value={gmail}
                        onChange={(e) => setGmail(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="city">City:</label>
                    <input
                        type="text"
                        id="city"
                        value={city}
                        onChange={(e) => setCity(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="province">Province:</label>
                    <input
                        type="text"
                        id="province"
                        value={province}
                        onChange={(e) => setProvince(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="country">Country:</label>
                    <input
                        type="text"
                        id="country"
                        value={country}
                        onChange={(e) => setCountry(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Subscribe</button>
                <button type="button" onClick={handleReset}>
                    Reset
                </button>
            </form>
            <p>{message}</p>
        </div>
    );
};

export default SubscriptionPage;