import React, { useEffect, useState } from 'react'

const BASE_URL = 'http://localhost:8080/api'

function useFetch(url, opts) {
  return fetch(url, opts).then(async r => {
    if (!r.ok) throw new Error((await r.text()) || r.statusText)
    return r.json().catch(() => null)
  })
}

function SmallInput({ label, value, onChange, type='text', placeholder='' }){
  return (
    <label className="block text-sm">
      <div className="text-xs text-slate-600">{label}</div>
      <input type={type} value={value} onChange={e => onChange(e.target.value)} placeholder={placeholder}
        className="mt-1 w-full px-2 py-1 border rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-300" />
    </label>
  )
}

export default function App(){
  const [book, setBook] = useState({ id:'', title:'', author:'', publisher:'', campus:'North' })
  const [student, setStudent] = useState({ id:'', name:'', department:'', campus:'North' })
  const [issue, setIssue] = useState({ id:'', bookId:'', studentId:'', issueDate:'', returnDate:null, campus:'North' })

  const [northBooks, setNorthBooks] = useState([])
  const [southBooks, setSouthBooks] = useState([])
  const [centralBooks, setCentralBooks] = useState([])
  const [unreturned, setUnreturned] = useState([])

  const [loading, setLoading] = useState(false)
  const [msg, setMsg] = useState(null)

  async function reloadCampusBooks(){
    try{
      const n = await useFetch(`${BASE_URL}/campus/North/books`)
      const s = await useFetch(`${BASE_URL}/campus/South/books`)
      setNorthBooks(n || [])
      setSouthBooks(s || [])
    }catch(err){ console.error(err); }
  }

  async function reloadCentral(){
    try{
      const c = await useFetch(`${BASE_URL}/central/books`)
      setCentralBooks(c || [])
    }catch(err){ console.error(err); }
  }

  async function reloadUnreturned(){
    try{
      const u = await useFetch(`${BASE_URL}/central/unreturned`)
      setUnreturned(u || [])
    }catch(err){ console.error(err); }
  }

  useEffect(()=>{
    reloadCampusBooks();
    reloadCentral();
    reloadUnreturned();
  }, [])

  async function doPost(path, body){
    setLoading(true)
    setMsg(null)
    try{
      const res = await useFetch(`${BASE_URL}${path}`, {
        method: 'POST', headers: {'Content-Type':'application/json'}, body: JSON.stringify(body)
      })
      setMsg('Success')
      setTimeout(()=>{
        reloadCampusBooks(); reloadCentral(); reloadUnreturned();
      }, 300)
      return res
    }catch(err){
      console.error(err); setMsg('Error: '+err.message)
      throw err
    }finally{ setLoading(false) }
  }

  return (
    <div className="min-h-screen bg-slate-50 p-6">
      <div className="max-w-6xl mx-auto">
        <header className="mb-6">
          <h1 className="text-3xl font-semibold">Library Management System</h1>
        </header>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div className="col-span-1 bg-white p-4 rounded shadow">
            <h2 className="font-medium">Add Book</h2>
            <div className="mt-3 space-y-2">
              <SmallInput label="Book ID" value={book.id} onChange={v=>setBook({...book,id:v})} />
              <SmallInput label="Title" value={book.title} onChange={v=>setBook({...book,title:v})} />
              <SmallInput label="Author" value={book.author} onChange={v=>setBook({...book,author:v})} />
              <SmallInput label="Publisher" value={book.publisher} onChange={v=>setBook({...book,publisher:v})} />
              <label className="block text-sm">
                <div className="text-xs text-slate-600">Campus</div>
                <select value={book.campus} onChange={e=>setBook({...book,campus:e.target.value})}
                  className="mt-1 w-full px-2 py-1 border rounded-md">
                  <option>North</option>
                  <option>South</option>
                </select>
              </label>

              <button className="mt-2 w-full bg-indigo-600 text-white py-2 rounded hover:bg-indigo-700"
                onClick={()=>doPost('/campus/book', book)}>Add Book</button>
            </div>
          </div>

          <div className="col-span-1 bg-white p-4 rounded shadow">
            <h2 className="font-medium">Add Student</h2>
            <div className="mt-3 space-y-2">
              <SmallInput label="Student ID" value={student.id} onChange={v=>setStudent({...student,id:v})} />
              <SmallInput label="Name" value={student.name} onChange={v=>setStudent({...student,name:v})} />
              <SmallInput label="Department" value={student.department} onChange={v=>setStudent({...student,department:v})} />
              <label className="block text-sm">
                <div className="text-xs text-slate-600">Campus</div>
                <select value={student.campus} onChange={e=>setStudent({...student,campus:e.target.value})}
                  className="mt-1 w-full px-2 py-1 border rounded-md">
                  <option>North</option>
                  <option>South</option>
                </select>
              </label>

              <button className="mt-2 w-full bg-green-600 text-white py-2 rounded hover:bg-green-700"
                onClick={()=>doPost('/campus/student', student)}>Add Student</button>
            </div>
          </div>

          <div className="col-span-1 bg-white p-4 rounded shadow">
            <h2 className="font-medium">Issue Book</h2>
            <div className="mt-3 space-y-2">
              <SmallInput label="Issue ID" value={issue.id} onChange={v=>setIssue({...issue,id:v})} />
              <SmallInput label="Book ID" value={issue.bookId} onChange={v=>setIssue({...issue,bookId:v})} />
              <SmallInput label="Student ID" value={issue.studentId} onChange={v=>setIssue({...issue,studentId:v})} />
              <SmallInput label="Issue Date (YYYY-MM-DD)" value={issue.issueDate} onChange={v=>setIssue({...issue,issueDate:v})} />
              <label className="block text-sm">
                <div className="text-xs text-slate-600">Campus</div>
                <select value={issue.campus} onChange={e=>setIssue({...issue,campus:e.target.value})}
                  className="mt-1 w-full px-2 py-1 border rounded-md">
                  <option>North</option>
                  <option>South</option>
                </select>
              </label>

              <button className="mt-2 w-full bg-orange-600 text-white py-2 rounded hover:bg-orange-700"
                onClick={()=>doPost('/campus/issue', issue)}>Issue Book</button>
            </div>
          </div>
        </div>

        <div className="mt-6 grid grid-cols-1 md:grid-cols-3 gap-4">
          <div className="bg-white p-4 rounded shadow col-span-1">
            <h3 className="font-medium">North Campus Books</h3>
            <div className="mt-3 space-y-2 max-h-64 overflow-auto">
              {northBooks.length===0 ? <div className="text-slate-500 text-sm">No books</div> : northBooks.map(b=> (
                <div key={b.id} className="p-2 border rounded">{b.id} — <strong>{b.title}</strong><div className="text-xs text-slate-500">{b.author} • {b.publisher}</div></div>
              ))}
            </div>
          </div>

          <div className="bg-white p-4 rounded shadow col-span-1">
            <h3 className="font-medium">South Campus Books</h3>
            <div className="mt-3 space-y-2 max-h-64 overflow-auto">
              {southBooks.length===0 ? <div className="text-slate-500 text-sm">No books</div> : southBooks.map(b=> (
                <div key={b.id} className="p-2 border rounded">{b.id} — <strong>{b.title}</strong><div className="text-xs text-slate-500">{b.author} • {b.publisher}</div></div>
              ))}
            </div>
          </div>

          <div className="bg-white p-4 rounded shadow col-span-1">
            <h3 className="font-medium">Central (Aggregated)</h3>
            <div className="mt-3 space-y-2 max-h-64 overflow-auto">
              {centralBooks.length===0 ? <div className="text-slate-500 text-sm">No books</div> : centralBooks.map(b=> (
                <div key={b.id} className="p-2 border rounded">{b.id} — <strong>{b.title}</strong><div className="text-xs text-slate-500">Campus: {b.campus}</div></div>
              ))}
            </div>
            <div className="mt-3 flex gap-2">
              <button className="flex-1 bg-sky-600 text-white py-2 rounded" onClick={reloadCentral}>Refresh</button>
              <button className="flex-1 bg-emerald-600 text-white py-2 rounded" onClick={async ()=>{ await useFetch(`${BASE_URL}/central/sync-books`, {method:'POST'}); reloadCentral(); }}>Sync Central</button>
            </div>
          </div>
        </div>

        <div className="mt-6 bg-white p-4 rounded shadow">
          <h3 className="font-medium">Unreturned Books (All Campuses)</h3>
          <div className="mt-3">
            {unreturned.length===0 ? <div className="text-slate-500">No unreturned records</div> : (
              <table className="w-full text-sm table-auto border-collapse">
                <thead className="text-left text-xs text-slate-600"><tr><th>ID</th><th>Book</th><th>Student</th><th>Issue Date</th><th>Campus</th></tr></thead>
                <tbody>
                  {unreturned.map(u => (
                    <tr key={u.id} className="border-t"><td className="py-2">{u.id}</td><td>{u.bookId}</td><td>{u.studentId}</td><td>{u.issueDate}</td><td>{u.campus}</td></tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>
        </div>

        <footer className="mt-6 text-sm text-slate-500">Status: {loading ? 'working...' : msg || 'idle'}</footer>
      </div>
    </div>
  )
}
