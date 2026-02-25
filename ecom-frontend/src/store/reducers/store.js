import {configureStore} from "@reduxjs/toolkit"
import { productReducer } from "./ProductReducer";


const store = configureStore({
  reducer:{
    products:productReducer,
  },
  preloadedState:{
    
  },
});

export default store;