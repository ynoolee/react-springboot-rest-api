import '../App.css'
import 'bootstrap/dist/css/bootstrap.css'
import {SummaryItem} from "./SummaryItem";
import {useState} from "react";

export const Summary = (props) => {
    const {items = [], onOrderSubmit} = props;
    // useState 를 통해, input 이 상태와 연결됨
    const [order, setOrder] = useState({
        email:"", address:"", postcode:"" // 초기값을 공백문자열로 세팅!
    }); // 각 태그와 연결시켜줘
    const totalPrice = items.reduce((prev, curr) => prev + (curr.price * curr.count), 0)
    // change 가 발생할 때 마다 콜백이 발생할 수 있도록 한다.
    // 각 필드에 맞는 콜백함수 만들어주자
    const handleEmailInputChanged = (e) => {
        // console.log(e.target.value); // 학습용 -> 이런식으로 콘솔에서 확인 가능
        setOrder({...order, email: e.target.value}) // 실제 타이핑한 값이 order 로 매핑되어야함.
    }
    const handleAddressInputChanged = (e) => {
        setOrder({...order, address: e.target.value})
    }
    const handlePostcodeInputChange = (e) => {
        setOrder({...order, postcode: e.target.value})}
    const handleSubmit = (e) => {
        // console.log(e);
        // 아주 간단한 검증만 해 주겠음
        if(order.address === "" || order.email === "" || order.postcode === "") {
            alert("입력값을 확인해주세요!");
        }else{
            onOrderSubmit(order);
        }
    }

    return (
        <>
            <div>
                <h5 className="m-0 p-0"><b>Summary</b></h5>
            </div>
            <hr/>
            {items.map(v => <SummaryItem key={v.productId} count={v.count} productName={v.productName}/>)}
            <form>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">이메일</label>
                    <input type="email" className="form-control mb-1" value={order.email} onChange={handleEmailInputChanged} id="email"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="address" className="form-label">주소</label>
                    <input type="text" className="form-control mb-1" value={order.address} onChange={handleAddressInputChanged} id="address"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="postcode" className="form-label">우편번호</label>
                    <input type="text" className="form-control" value={order.postcode} onChange={handlePostcodeInputChange} id="postcode"/>
                </div>
                <div>당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.</div>
            </form>
            <div className="row pt-2 pb-2 border-top">
                <h5 className="col">총금액</h5>
                <h5 className="col text-end">{totalPrice}원</h5>
            </div>
            <button className="btn btn-dark col-12" onClick={handleSubmit}>결제하기</button>
        </>
    )
}