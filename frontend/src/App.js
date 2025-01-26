import Home from "./pages/Home";
import About from "./pages/About";
import Counter from "./pages/Counter";
import Input from "./pages/Input";
import Input2 from "./pages/Input2";
import { Routes, Route, Link, BrowserRouter } from "react-router-dom";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
      <nav class="text-purple-600">
        <Link to="/">Home</Link> | <Link to="/about">About</Link> | <Link to="/counter">Counter</Link> |{" "}
        <Link to={"/input"}>Input</Link> | <Link to={"/input2"}>Input2</Link>
      </nav>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/about" element={<About/>}/>
        <Route path="/counter" element={<Counter/>}/>
        <Route path="/input" element={<Input/>}/>
        <Route path="/input2" element={<Input2/>}/>
      </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
