import React, {useState} from "react";

const Counter = () => {
    const [count, setCount] = useState(0);

    const increase = () => {
        setCount(count + 1);
    }

    const decrease = () => {
        setCount(count - 1);
    }

    return (
        <div>
            <button class="font-bold underline text-sky-600" onClick={increase}>
                증가
            </button>
            <button class="font-bold underline text-red-600" onClick={decrease}>
                감소
            </button>
            <p class="text-4xl">{count}</p>
        </div>
    )
}

export default Counter;