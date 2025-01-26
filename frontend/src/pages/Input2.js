// import React, { useState } from "react";

// const Input2 = () => {
//     const [inputs, setInputs] = useState({
//         name: "",
//         email: "",
//         phone: ""
//     });

//     const textChange = (e) => {
//         const value = e.target.value;
//         const id = e.target.id;

//         setInputs({
//             ...inputs
//         });
//     }

//     return (
//     <div>
//         <div>
//             <label>이름</label>
//             <input type="text" id="name" value={name} onChange={textChange}></input>
//         </div>
//         <div>
//             <label>이메일</label>
//             <input type="text" id="email" value={email} onChange={textChange}></input>
//         </div>
//         <div>
//             <label>번호</label>
//             <input type="text" id="phone" value={phone} onChange={textChange}></input>
//         </div>
//     </div>    
//     )
// }

// export default Input2;