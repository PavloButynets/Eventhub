import useAuth from "../../hooks/useAuth"


const Home = () =>{
    
    return(
    localStorage.getItem("token")?<h1>Hello</h1>:<h2>No hello</h2>
    )
}

export default Home