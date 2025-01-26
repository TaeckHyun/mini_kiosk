import React, { useState } from "react";

const Input = () => {
    const [input, setInput] = useState("");

    const textChange = (e) => {
        setInput(e.target.value);
    }

    return (
        <div>
            <input class="border border-gray-600 rounded-md p-2" type="text" value={input} onChange={textChange}></input>
            <br />
            <p>{input}</p>
        </div>
    )
}

export default Input;